package com.services.project;

import com.database.entity.*;
import com.database.repository.CourseRepository;
import com.database.repository.ProjectRepository;
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

@Service
public class ProjectService {

    public static final List<String> ADD_STUDENT_TEMPLATE = Lists.newArrayList("IMIE, NAZWISKO, NR_ERES, NR_STUDENTA");
    public static final List<String> ADD_TUTOR_TEMPLATE = Lists.newArrayList("IMIE, NAZWISKO, NR_ERES, ILOSC_ZESPOŁÓW");

    private static final int ERES_NR_POSITION = 2;
    private static final int TEAMS_NUMBER_POSITION = 3;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

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
        File fileOfStudents = fileService.getFile(request.getFileStudentData(), ADD_STUDENT_TEMPLATE);
        File fileOfTeams = fileService.getFile(request.getFileStudentData(), ADD_TUTOR_TEMPLATE);
        int groupId = findCourseWithName(request.getCourseName()).getGroupId();
        String privateToken = request.getPrivateToken();
        List<User> students = fileService.getObjectsFromFile(fileOfStudents, usersForProject());
        List<Team> teams = fileService.getObjectsFromFile(fileOfTeams, createTeamsForProject(groupId, privateToken));
        saveProject(request, teams, students);
    }

    private Function<List<String>, List<User>> usersForProject() {
        return l -> Lists.newArrayList(userRepository.findUserByEres(l.get(ERES_NR_POSITION)));
    }

    private Function<List<String>, List<Team>> createTeamsForProject(int groupId, String privateToken) {
        return l -> {
            User tutor = userRepository.findUserByEres(l.get(ERES_NR_POSITION));
            int numberOfTeams = Integer.parseInt(l.get(TEAMS_NUMBER_POSITION));
            List<Team> teams = Lists.newArrayList();
            for (int i = 0; i < numberOfTeams; ++i) {
                teams.add(createTeam(groupId, tutor, privateToken));
            }
            return teams;
        };
    }

    private Team createTeam(int groupId, User tutor, String privateToken) {
        gitLabApi.createProject(privateToken, tutor.getName(), groupId);
        Team team = new Team();
        team.setConfirmed(TeamState.PENDING);
        team.setTutor(tutor);
        team.setName(tutor.getName());
        return team;
    }

    @Transactional
    private void saveProject(ProjectCreationRequest request, List<Team> teams, List<User> students) {
        Project project = new Project();
        project.setStartDate(request.getStartDate());
        project.setCourse(findCourseWithName(request.getCourseName()));
        project.setTeams(teams);
        project.setMaxPoints(request.getPoints());
        project.setNextDate(request.getEndDate());
        project.setStudents(students);
        projectRepository.save(project);
    }

    @Transactional
    private Course findCourseWithName(String name) {
        return courseRepository.findCourseByAbbreviation(name);
    }

}
