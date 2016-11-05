package com.services.student;

import com.database.entity.*;
import com.database.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
        User student = getUserWithTeamsAndProjects(login);
        List<Team> teamsOfStudent =  student.getTeamsAsStudent().stream()
                .map(UserTeam::getTeam)
                .collect(Collectors.toList());
        List<Team> emptyTeams = student.getProjectsAsStudent().stream()
                .filter(p -> !isStudentHasAcceptedTeamForProject(student.getTeamsAsStudent(), p))
                .flatMap(p -> p.getTeams().stream())
                .filter(t -> t.getConfirmed() == TeamState.EMPTY)
                .collect(Collectors.toList());
        teamsOfStudent.addAll(emptyTeams);
        return teamsOfStudent.stream()
                .sorted((a, b) -> Integer.compare(a.getConfirmed().ordinal(), b.getConfirmed().ordinal()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private boolean isStudentHasAcceptedTeamForProject(List<UserTeam> teams, Project project) {
        return teams.stream()
                .filter(ut -> ut.getTeam().getProject().equals(project))
                .anyMatch(UserTeam::isConfirmed);
    }

    public TeamResponse getTeamForStudentsId(long id, String login) {
        User student = userRepository.findUserByLogin(login);
        Team team = getTeam(id);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setConfirmedTeam(team.getConfirmed().name());
        teamResponse.setGitlabPage(team.getGitlabPage());
        teamResponse.setTopic(team.getTopic());
        List<String> studentNames = team.getStudents()
                .stream()
                .map(UserTeam::getStudent)
                .map(User::name)
                .collect(Collectors.toList());
        teamResponse.setStudents(studentNames);
        teamResponse.setConfirmedUser(userTeam.isConfirmed());
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
        dto.setTutor(team.getTutor().name());
        dto.setId(team.getId());
        return dto;
    }

    @Transactional
    private User getUserWithTeamsAndProjects(String login) {
        User student = userRepository.findUserByLogin(login);
        if(student != null) {
            student.getTeamsAsStudent().size();
            student.getProjectsAsStudent().size();
            for(Project p: student.getProjectsAsStudent())
                p.getTeams().size();
        }
        return student;
    }

    @Transactional
    private Team getTeam(long id) {
        Team team = teamRepository.findById(id);
        if(team != null)
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
        userTeamRepository.save(userTeam);
    }

    @Transactional
    public void deleteStudent(long teamId, long studentId) {
        User student = userRepository.findUsersWithTeam(studentId);
        Team team = teamRepository.findTeamWithStudents(teamId);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
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
        Team team = teamRepository.findById(teamId);
        team.setConfirmed(TeamState.PENDING);
    }

    @Transactional
    public void acceptRequest(long teamId, String login) {
        Team team = teamRepository.findById(teamId);
        User student = userRepository.findUserByLogin(login);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
        userTeam.setConfirmed(true);

        List<UserTeam> userTeamsForProject = userTeamRepository.findAllUserTeamsForProject(team.getProject().getId());
        Set<UserTeam> toRemove = userTeamsForProject.stream()
                .filter(Objects::nonNull)
                .filter(u -> !u.equals(userTeam))
                .filter(u -> u.getStudent().equals(student))
                .collect(Collectors.toSet());

        toRemove.forEach(u -> {
            u.getStudent().getTeamsAsStudent().remove(u);
            u.getTeam().getStudents().remove(u);
            userTeamRepository.delete(u);
        });
    }

    @Transactional
    public void takeTeam(long teamId, String login) {
        User student = userRepository.findUserByLogin(login);
        long studentId = student.getId();
        addStudent(teamId, studentId);
        acceptRequest(teamId, login);
        Team team = teamRepository.findById(teamId);
        team.setConfirmed(TeamState.FORMING);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
        userTeam.setLeader(true);
        teamRepository.save(team);
        userTeamRepository.save(userTeam);
    }
}
