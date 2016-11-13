package com.services.project;

import com.database.entity.*;
import com.database.repository.*;
import com.gitlab.GitLabApi;
import com.google.common.collect.Lists;
import com.services.file.File;
import com.services.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);

    private enum StudentsFile {
        IMIE, NAZWISKO, NR_ERES, NR_STUDENTA;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(u -> u.name()).collect(Collectors.toList());
        }
    }

    private enum CoursesFile {
        IMIE, NAZWISKO, NR_ERES, ILOSC_ZESPOLOW;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(u -> u.name()).collect(Collectors.toList());
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDeadlineRepository projectDeadlineRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private GitLabApi gitLabApi;

    @Transactional
    public List<CourseDto> findAllCoursesForUser(String login) {
        return courseRepository.findAllCoursesWithLecturerLogin(login).stream()
                .map(this::toCourseDto)
                .collect(Collectors.toList());
    }

    private CourseDto toCourseDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setAbbreviation(course.getAbbreviation());
        dto.setId(course.getId());
        dto.setName(course.getCourseName());
        return dto;
    }

    public List<LecturerTeamDto> findAllTeamsForCourse(long courseId) {
        return courseRepository.findTeamsForCourse(courseId).stream()
                .filter(Objects::nonNull)
                .map(this::toLecturerTeamDto)
                .collect(Collectors.toList());
    }

    private LecturerTeamDto toLecturerTeamDto(Team team) {
        LecturerTeamDto dto = new LecturerTeamDto();
        dto.setId(team.getId());
        dto.setTopic(team.getTopic());
        dto.setStatus(team.getConfirmed().name());
        dto.setTutor(team.getTutor().name());
        return dto;
    }

    public void createProject(ProjectCreationRequest request) {
        File fileOfStudents = fileService.getFile(request.getFileStudentData(), StudentsFile.getAllNames());
        File fileOfTeams = fileService.getFile(request.getFileTutorData(), CoursesFile.getAllNames());
        //int groupId = courseRepository.findOne(request.getCourseId()).getGroupId();
        int groupId = 0;
        String privateToken = request.getPrivateToken();
        List<User> students = fileService.getObjectFromFile(fileOfStudents, usersForProject());
        List<Team> teams = fileService.getObjectsFromFile(fileOfTeams, createTeamsForProject(groupId, privateToken));
        saveProject(request, teams, students);
    }

    private Function<List<String>, User> usersForProject() {
        return l -> userRepository.findUserByEresWithProjects(l.get(StudentsFile.NR_ERES.ordinal()));
    }

    private Function<List<String>, List<Team>> createTeamsForProject(int groupId, String privateToken) {
        return l -> {
            User tutor = userRepository.findUserByEres(l.get(CoursesFile.NR_ERES.ordinal()));
            int numberOfTeams = Integer.parseInt(l.get(CoursesFile.ILOSC_ZESPOLOW.ordinal()));
            List<Team> teams = Lists.newArrayList();
            for (int i = 0; i < numberOfTeams; ++i) {
                teams.add(createTeam(groupId, tutor, privateToken));
            }
            return teams;
        };
    }

    private Team createTeam(int groupId, User tutor, String privateToken) {
        //ProjectDto dto = gitLabApi.createProject(privateToken, tutor.name(), groupId);
        Team team = new Team();
        team.setConfirmed(TeamState.EMPTY);
        team.setTutor(tutor);
        team.setName(tutor.name());
//        team.setId(Long.valueOf(dto.getId()));
//        team.setGitlabPage(dto.getPath());
        return team;
    }

    @Transactional
    private Project saveProject(ProjectCreationRequest request, List<Team> teams, List<User> students) {
        LOG.info("saving project with course {}, teams of size {} and students of size {}", request.getCourseId(), teams.size(), students.size());
        Project project = new Project();
        project.setCourse(courseRepository.findOne(request.getCourseId()));
        project.setTeams(teams);
        project.setStudentsNumber(request.getStudentsNumber());
        project.setMaxPoints(request.getDeadlines().stream()
                .mapToInt(ProjectDeadlineDto::getPoints)
                .sum());
        project.setStudents(students);
        project = projectRepository.save(project);
        request.getDeadlines().forEach(this::saveProjectDeadline);
        for (Team team : teams) {
            team.setProject(project);
        }
        teamRepository.save(teams);
        return project;
    }


    @Transactional
    public void acceptTeam(long teamId) {
        Team team = teamRepository.findById(teamId);
        team.setConfirmed(TeamState.ACCEPTED);
    }

    @Transactional
    private void saveProjectDeadline(ProjectDeadlineDto dto) {
        ProjectDeadline deadline = new ProjectDeadline();
        deadline.setDate(dto.getDate());
        deadline.setDescription(dto.getDescription());
        deadline.setPoints(dto.getPoints());
        projectDeadlineRepository.save(deadline);
    }
}
