package com.services.student;

import com.database.entity.*;
import com.database.repository.*;
import com.google.common.base.Preconditions;
import com.services.project.ProjectDeadlineDto;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

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
        List<Team> teamsOfStudent = student.getTeamsAsStudent().stream()
                .map(UserTeam::getTeam)
                .collect(Collectors.toList());
        List<Team> emptyTeams = student.getProjectsAsStudent().stream()
                .filter(p -> !isStudentHasAcceptedTeamForProject(student.getTeamsAsStudent(), p))
                .flatMap(p -> p.getTeams().stream())
                .filter(t -> t.getConfirmed() == TeamState.EMPTY)
                .collect(Collectors.toList());
        teamsOfStudent.addAll(emptyTeams);
        return teamsOfStudent.stream()
                .sorted(Comparator.comparingInt(a -> a.getConfirmed().ordinal()))
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
        Team team = teamRepository.findTeamWithStudents(id);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setTeamState(team.getConfirmed().name());
        teamResponse.setGitlabPage(team.getGitlabPage());
        teamResponse.setPoints(team.getPoints());
        teamResponse.setTopic(team.getTopic());
        teamResponse.setDates(team.getProject().getDeadlines().stream()
                .filter(p -> p.getDate().isAfter(LocalDate.now()))
                .map(this::toDeadlineDto)
                .collect(Collectors.toList()));
        List<UserWithIdDto> studentNames = team.getStudents()
                .stream()
                .map(UserTeam::getStudent)
                .map(UserWithIdDto::new)
                .collect(Collectors.toList());
        teamResponse.setStudents(studentNames);
        teamResponse.setNumberOfStudents(team.getProject().getStudentsNumber());
        teamResponse.setConfirmedUser(userTeam.isConfirmed());
        teamResponse.setLeader(userTeam.isLeader());
        teamResponse.setCanBeAccepted(teamCanBeAccepted(team));

        LOG.info("students response for login {} and team id {}: {}", login, id, teamResponse);

        return teamResponse;
    }

    private boolean teamCanBeAccepted(Team team) {
        return team.getStudents().stream().allMatch(UserTeam::isConfirmed)
                && team.getStudents().size() == team.getProject().getStudentsNumber();
    }

    private ProjectDeadlineDto toDeadlineDto(ProjectDeadline projectDeadline) {
        ProjectDeadlineDto dto = new ProjectDeadlineDto();
        dto.setDate(projectDeadline.getDate());
        dto.setDescription(projectDeadline.getDescription());
        dto.setPoints(projectDeadline.getPoints());
        return dto;
    }

    @Transactional
    public List<User> findAllStudentsForTeam(long teamId) {
        Long id = teamRepository.findById(teamId).getProject().getId();
        Project project =  projectRepository.findProjectWithTeams(id);
        List<Team> teams = project.getTeams();
        return loadUserWithProjects(project.getId())
                .stream()
                .filter(checkIfUserNotBelongToAnyTeam(teams))
                .collect(Collectors.toList());

    }

    @Transactional(propagation = REQUIRES_NEW)
    private Set<User> loadUserWithProjects(long projectId) {
        Set<User> users = userRepository.findUsersForProject(projectId);
        for (User u : users) {
            Hibernate.initialize(u.getTeamsAsStudent());
        }
        return users;
    }

    private StudentsProjectDto toDto(Team team) {
        StudentsProjectDto dto = new StudentsProjectDto();
        Project project = team.getProject();
        dto.setCourseName(project.getCourse().getAbbreviation());
        dto.setNextDate(project.getDeadlines().stream()
                .map(ProjectDeadline::getDate)
                .filter(date -> date.isAfter(LocalDate.now()))
                .findFirst().orElse(null));
        dto.setProjectName(team.getTopic());
        dto.setState(team.getConfirmed().name());
        dto.setTutor(team.getTutor().name());
        dto.setId(team.getId());
        return dto;
    }

    @Transactional
    private User getUserWithTeamsAndProjects(String login) {
        User student = userRepository.findUserByLogin(login);
        if (student != null) {
            student.getTeamsAsStudent().size();
            student.getProjectsAsStudent().size();
            for (Project p : student.getProjectsAsStudent())
                p.getTeams().size();
        }
        return student;
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
    public StudentRemovalResponse deleteStudent(long teamId, long studentId, String userLogin) {
        User student = userRepository.findUsersWithTeam(studentId);
        Team team = teamRepository.findTeamWithStudents(teamId);

        boolean selfRemove = StringUtils.equals(userLogin, student.getLogin());

        if(team.getStudents().size() == 1 || selfRemove) {
            team.getStudents().forEach(ut -> userTeamRepository.delete(ut));
            team.setConfirmed(TeamState.EMPTY);
            team.setTopic(null);
        } else {
            UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
            userTeamRepository.delete(userTeam);
        }

        StudentRemovalResponse response = new StudentRemovalResponse();
        response.setSelfRemove(selfRemove);
        return response;
    }

    @Transactional
    public List<Topic> findTopicsForTeam(long teamId) {
        Team team = teamRepository.findById(teamId);
        Course course = team.getProject().getCourse();
        User tutor = team.getTutor();

        return topicRepository.findTopicsByCourseAndUser(course, tutor);
    }

    @Transactional
    public void saveTopic(long teamId, String topic) {
        Team team = teamRepository.findById(teamId);
        team.setTopic(topic);
        teamRepository.save(team);
    }

    @Transactional
    public void acceptTeam(long teamId, String login) {
        Team team = teamRepository.findTeamWithStudents(teamId);
        User studnet = userRepository.findUserByLogin(login);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, studnet);

        boolean allAcceptedStudents = team.getStudents().stream().allMatch(UserTeam::isConfirmed);

        Preconditions.checkArgument(allAcceptedStudents, "All students must be confirmed for team {} to accept", teamId);
        Preconditions.checkArgument(userTeam.isLeader(), "User with login {} is not leader of team {}", login, teamId);

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
