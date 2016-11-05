package com.services.project;

import com.database.entity.*;
import com.database.repository.CourseRepository;
import com.database.repository.ProjectRepository;
import com.database.repository.TeamRepository;
import com.database.repository.UserRepository;
import com.gitlab.GitLabApi;
import com.google.common.collect.Lists;
import com.services.file.File;
import com.services.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectService {

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
    private FileService fileService;

    @Autowired
    private GitLabApi gitLabApi;

    @Transactional
    public List<String> findAllCoursesForUser(String login) {
        return courseRepository.findAllCoursesWithLecturerLogin(login);
    }

    public void createProject(ProjectCreationRequest request) {
        File fileOfStudents = fileService.getFile(request.getFileStudentData(), StudentsFile.getAllNames());
        File fileOfTeams = fileService.getFile(request.getFileTutorData(), CoursesFile.getAllNames());
        int groupId = findCourseWithName(request.getCourseName()).getGroupId();
        String privateToken = request.getPrivateToken();
        List<User> students = fileService.getObjectFromFile(fileOfStudents, usersForProject());
        List<Team> teams = fileService.getObjectsFromFile(fileOfTeams, createTeamsForProject(groupId, privateToken));
        Project project = saveProject(request, teams, students);
 //       addProjectToStudents(project, students);
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
        //gitLabApi.createProject(privateToken, tutor.name(), groupId);
        Team team = new Team();
        team.setConfirmed(TeamState.EMPTY);
        team.setTutor(tutor);
        team.setName(tutor.name());
        return team;
    }

    @Transactional
    private Project saveProject(ProjectCreationRequest request, List<Team> teams, List<User> students) {
        Project project = new Project();
        project.setStartDate(request.getStartDate());
        project.setCourse(findCourseWithName(request.getCourseName()));
        project.setTeams(teams);
        project.setMaxPoints(request.getPoints());
        project.setNextDate(request.getEndDate());
        project.setStudents(students);
        project = projectRepository.save(project);
        for(Team team : teams){
            team.setProject(project);
        }
        teamRepository.save(teams);
        return project;
    }

    @Transactional
    private void addProjectToStudents(Project project, List<User> students) {
        students.forEach(u-> {
            u.getProjectsAsStudent().add(project);
            userRepository.save(u);
        });
    }

    @Transactional
    private Course findCourseWithName(String name) {
        return courseRepository.findCourseByAbbreviation(name);
    }

    @Transactional
    public void acceptTeam(long teamId) {
        Team team = teamRepository.findById(teamId);
        team.setConfirmed(TeamState.ACCEPTED);
    }
}
