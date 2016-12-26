package com.services.project;

import com.database.entity.*;
import com.database.repository.*;
import com.gitlab.GitLabApi;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.services.file.File;
import com.services.file.FileService;
import com.services.mail.MailService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);

    private enum StudentsFile {
        IMIE, NAZWISKO, NR_ERES, NR_STUDENTA;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(Enum::name).collect(Collectors.toList());
        }
    }

    private enum CoursesFile {
        IMIE, NAZWISKO, NR_ERES, ILOSC_ZESPOLOW;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(Enum::name).collect(Collectors.toList());
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

    @Autowired
    private MailService mailService;

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

    public ProjectTeamResponse getProjectResponse(long courseId) {
        ProjectTeamResponse response = new ProjectTeamResponse();
        Set<Project> projects = initializeProjectsWithStudentsAndTeams(courseId);
        List<LecturerTeamDto> teamsDtos = projects.stream()
                .flatMap(p -> p.getTeams().stream())
                .filter(Objects::nonNull)
                .map(this::toLecturerTeamDto)
                .collect(Collectors.toList());

        response.setTeams(teamsDtos);
        response.setMailAddresses(mailService.getAddressForSubject(projects));
        LOG.info("Found course response {}", response);
        return response;
    }

    private Set<Project> initializeProjectsWithStudentsAndTeams(long courseId) {
        Set<Project> projects = courseRepository.findTeamsWithUsersForCourse(courseId);
        projects.removeIf(Objects::isNull);
        projects.forEach(p -> Hibernate.initialize(p.getStudents()));
        return projects;
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
        LOG.info("Creating project with request {}", request);

        Course course = courseRepository.findCourseByIdWithProjects(request.getCourseId());
        Preconditions.checkNotNull(course, "Cannot create project for empty course");

        File fileOfStudents = fileService.getFile(request.getFileStudentData(), StudentsFile.getAllNames());
        File fileOfTeams = fileService.getFile(request.getFileTutorData(), CoursesFile.getAllNames());
        List<User> students = fileService.getObjectFromFile(fileOfStudents, usersForProject());
        List<Team> teams = fileService.getObjectsFromFile(fileOfTeams, createTeamsForProject());
        saveProject(request, teams, students);
    }

    private Function<List<String>, User> usersForProject() {
        return l -> userRepository.findUserByEresWithProjects(l.get(StudentsFile.NR_ERES.ordinal()));
    }

    private Function<List<String>, List<Team>> createTeamsForProject() {
        return l -> {
            User tutor = userRepository.findUserByEres(l.get(CoursesFile.NR_ERES.ordinal()));
            Preconditions.checkNotNull(tutor, String.format("Cannot find user with eres number %s", l.get(CoursesFile.NR_ERES.ordinal())));

            int numberOfTeams = Integer.parseInt(l.get(CoursesFile.ILOSC_ZESPOLOW.ordinal()));
            List<Team> teams = Lists.newArrayList();
            for (int i = 0; i < numberOfTeams; ++i) {
                teams.add(createTeam(tutor));
            }
            return teams;
        };
    }

    private Team createTeam(User tutor) {
        Team team = new Team();
        team.setConfirmed(TeamState.EMPTY);
        team.setTutor(tutor);
        team.setPoints(0);
        return team;
    }

    @Transactional
    private Project saveProject(ProjectCreationRequest request, List<Team> teams, List<User> students) {
        LOG.info("saving project with course {}, teams of size {} and students of size {}", request.getCourseId(), teams.size(), students.size());
        Project project = new Project();
        project.setCourse(courseRepository.findOne(request.getCourseId()));
        project.setTeams(teams);
        project.setMinStudentsNumber(request.getMinStudentsNumber());
        project.setMaxStudentsNumber(request.getMaxStudentsNumber());
        project.setMaxPoints(request.getDeadlines().stream()
                .mapToInt(ProjectDeadlineDto::getPoints)
                .sum());
        project.setStudents(students);
        addUsersToGroup(project, request.getPrivateToken());
        projectRepository.save(project);
        request.getDeadlines().forEach(d -> saveProjectDeadline(d, project));
        for (Team team : teams) {
            team.setProject(project);
        }
        teamRepository.save(teams);
        return project;
    }

    @Transactional
    private void saveProjectDeadline(ProjectDeadlineDto dto, Project project) {
        ProjectDeadline deadline = new ProjectDeadline();
        deadline.setDate(dto.getDate());
        deadline.setDescription(dto.getDescription());
        deadline.setPoints(dto.getPoints());
        deadline.setProject(project);
        projectDeadlineRepository.save(deadline);
    }

    private void addUsersToGroup(Project project, String privateToken) {
        Set<User> users = Sets.newHashSet(project.getStudents());
        users.addAll(project.getTeams().stream().map(Team::getTutor).collect(Collectors.toList()));
        users.remove(project.getCourse().getLecturer());
        List<Integer> usersId = users.stream().map(User::getGitlabId).collect(Collectors.toList());
        gitLabApi.addUsersToGroup(usersId, privateToken, project.getCourse().getGroupId());
    }

    public ProjectRecordsDto getRecordsForCourse(long courseId) {
        Set<Project> courseProjects = courseRepository.findTeamsWithUsersForCourse(courseId);
        return courseProjects.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingLong(Project::getId))
                .skip(courseProjects.size() - 1)
                .map(this::toRecordsDto)
                .findFirst()
                .orElseGet(ProjectRecordsDto::new);
    }

    private ProjectRecordsDto toRecordsDto(Project project) {
        Hibernate.initialize(project.getStudents());

        ProjectRecordsDto recordsDto = new ProjectRecordsDto();
        project.getTeams().forEach(t -> Hibernate.initialize(t.getStudents()));
        long allStudents = project.getStudents().size();
        long signedStudents = project.getTeams().stream()
                .filter(t -> t.getConfirmed() == TeamState.ACCEPTED)
                .mapToLong(t -> t.getStudents().size())
                .sum();
        long waitingStudents = project.getTeams().stream()
                .filter(t -> t.getConfirmed() == TeamState.PENDING)
                .mapToLong(t -> t.getStudents().size())
                .sum();
        recordsDto.setNumberOfStudents(allStudents);
        recordsDto.setSignedStudents(signedStudents);
        recordsDto.setWaitingStudents(waitingStudents);
        recordsDto.setUnsignedStudents(allStudents - signedStudents - waitingStudents);
        return recordsDto;
    }
}
