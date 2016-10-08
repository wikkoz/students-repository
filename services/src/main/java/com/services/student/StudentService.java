package com.services.student;

import com.database.entity.*;
import com.database.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<StudentsProjectDto> getProjectsOfStudent(String login) {
        User student = getUserWithTeams(login);
        return student.getTeamsAsStudent().stream()
                .map(UserTeam::getTeam)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TeamResponse getTeamForStudentsId(long id) {
        Team team = getTeam(id);
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setConfirmed(team.getConfirmed().name());
        teamResponse.setGitlabPage(team.getGitlabPage());
        teamResponse.setTopic(team.getTopic());
        List<String> studentNames = team.getStudents()
                .stream()
                .map(UserTeam::getStudent)
                .map(User::getName)
                .collect(Collectors.toList());
        teamResponse.setStudents(studentNames);
        return teamResponse;
    }

    @Transactional
    public List<User> findAllStudentsForTeam(long teamId) {
        Project project = getProjectWithTeams(teamRepository.findById(teamId).getProject().getId());
        List<Team> teams = project.getTeams();
        return loadUserWithProjects(project.getId())
                .stream()
                .filter(checkIfUserNotBelongToAnyTeam(teams))
                .collect(Collectors.toList());

    }

    @Transactional(propagation = REQUIRES_NEW)
    private Set<User> loadUserWithProjects(long projectId) {
        Set<User> users = userRepository.findUsersForProject(projectId);
        for(User u: users){
            Hibernate.initialize(u.getTeamsAsStudent());
        }
        return users;
    }

    private StudentsProjectDto toDto(Team team) {
        StudentsProjectDto dto = new StudentsProjectDto();
        Project project = team.getProject();
        dto.setCourseName(project.getCourse().getAbbreviation());
        dto.setNextDate(project.getNextDate());
        dto.setProjectName(team.getTopic());
        dto.setState(team.getConfirmed().name());
        dto.setTutor(team.getTutor().getName());
        return dto;
    }

    @Transactional
    private User getUserWithTeams(String login) {
        User student = userRepository.findUserByLogin(login);
        student.getTeamsAsStudent().size();
        return student;
    }

    @Transactional
    private Team getTeam(long id) {
        Team team = teamRepository.findById(id);
        team.getStudents().size();
        return team;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Project getProjectWithTeams(long id) {
        return projectRepository.findProjectWithTeams(id);
    }

    private Predicate<User> checkIfUserNotBelongToAnyTeam(List<Team> teams) {
        return u ->
                u.getTeamsAsStudent().stream()
                        .map(UserTeam::getTeam)
                        .noneMatch(teams::contains);
    }

    @Transactional
    public void addStudent(long teamId, long studentId) {
        User student = userRepository.findUsersWithTeam(studentId);
        Team team = teamRepository.findTeamWithStudents(teamId);
        UserTeam userTeam = new UserTeam();
        userTeam.setConfirmed(false);
        userTeam.setStudent(student);
        userTeam.setTeam(team);
        team.getStudents().add(userTeam);
        student.getTeamsAsStudent().add(userTeam);

    }

    @Transactional
    public void deleteStudent(long teamId, long studentId) {
        User student = userRepository.findUsersWithTeam(studentId);
        Team team = teamRepository.findTeamWithStudents(teamId);
        UserTeam userTeam = userTeamRepository.findUserTeamByStudentAndTeam(team, student);
        student.getTeamsAsStudent().remove(userTeam);
        team.getStudents().remove(userTeam);
        userTeamRepository.delete(userTeam);
    }

    @Transactional
    public List<Topic> findTopicsForTeam(long teamId) {
        Team team = teamRepository.findById(teamId);
        Course course = team.getProject().getCourse();
        User tutor = team.getTutor();

        return topicRepository.findTopicsByCourseAndUser(course, tutor);
    }

    @Transactional
    public void saveTopic(long teamId, long topicId) {
        Topic topic = topicRepository.findOne(topicId);
        Team team = teamRepository.findById(teamId);

        team.setTopic(topic.getTopic());
    }

    @Transactional
    public void acceptTeam(long teamId) {

    }
}
