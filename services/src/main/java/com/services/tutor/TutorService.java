package com.services.tutor;

import com.database.entity.*;
import com.database.repository.*;
import com.gitlab.GitLabApi;
import com.gitlab.project.ProjectDto;
import com.google.common.collect.Lists;
import com.services.mail.MailService;
import com.services.project.ProjectDeadlineDto;
import com.services.student.UserWithIdDto;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class TutorService {

    private final Logger LOG = LoggerFactory.getLogger(TutorService.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private MailService mailService;

    public List<SignedRecordsResponse> getAllRecords(String login) {
        User tutor = userRepository.findUserByLogin(login);
        Map<String, List<Team>> teams = teamRepository.findTeamByTutorWithStudents(tutor).stream()
                .collect(Collectors.groupingBy(t -> t.getProject().getCourse().getAbbreviation()));

        return teams.entrySet().stream().map(this::toRecordResponse)
                .collect(Collectors.toList());
    }

    public TutorTeamResponse getTeamResponse(long id) {
        Team team = teamRepository.findTeamWithStudents(id);
        TutorTeamResponse response = new TutorTeamResponse();
        response.setPoints(team.getPoints());
        response.setGitlabPage(team.getGitlabPage());
        response.setTopic(team.getTopic());
        response.setStudents(team.getStudents().stream()
                .map(UserTeam::getStudent)
                .map(UserWithIdDto::new)
                .collect(Collectors.toList()));
        response.setDeadlines(team.getProject().getDeadlines().stream()
                .map(this::toDeadlineDto)
                .collect(Collectors.toList()));
        response.setMailAddresses(mailService.getAddressesForTutor(team));
        response.setDescription(team.getDescription());

        LOG.info("Found team with {}", response);
        return response;
    }

    private ProjectDeadlineDto toDeadlineDto(ProjectDeadline deadline) {
        ProjectDeadlineDto dto = new ProjectDeadlineDto();
        dto.setPoints(deadline.getPoints());
        dto.setDescription(deadline.getDescription());
        dto.setDate(deadline.getDate());
        return dto;
    }

    public List<TutorProjectResponse> getAllTeams(String login) {
        LOG.info("getting all teams for tutor {}", login);
        User tutor = userRepository.findUserByLogin(login);
        return teamRepository.findAllTeamsByTutor(tutor).stream()
                .filter(t -> t.getConfirmed() == TeamState.ACCEPTED)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TutorProjectResponse toResponse(Team team) {
        TutorProjectResponse response = new TutorProjectResponse();
        response.setTeamId(team.getId());
        response.setTopic(team.getTopic());
        response.setCourseName(team.getProject().getCourse().getAbbreviation());
        Optional<ProjectDeadline> deadline = findNextDeadline(team.getProject().getDeadlines());
        response.setDeadlineDate(deadline.map(ProjectDeadline::getDate).orElse(null));
        response.setDeadlineDescription(deadline.map(ProjectDeadline::getDescription).orElse("Projekt zakonczony"));

        LOG.debug("found team for tutor {}", response.toString());
        return response;
    }

    private Optional<ProjectDeadline> findNextDeadline(List<ProjectDeadline> deadlines) {
        return deadlines.stream()
                .filter(d -> d.getDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(ProjectDeadline::getDate))
                .findFirst();
    }

    private SignedRecordsResponse toRecordResponse(Map.Entry<String, List<Team>> entry) {
        SignedRecordsResponse response = new SignedRecordsResponse();
        long signed = entry.getValue().stream()
                .filter(t -> t.getConfirmed() == TeamState.ACCEPTED)
                .count();
        long waiting = entry.getValue().stream()
                .filter(t -> t.getConfirmed() == TeamState.PENDING)
                .count();
        response.setWaitingTeams(waiting);
        response.setName(entry.getKey());
        response.setSignedTeams(signed);
        long allTeams = entry.getValue().size();
        response.setAllTeams(allTeams);
        return response;
    }

    public List<PendingTeamDto> getPendingTeams(String login) {
        Set<Team> teams = teamRepository.findTeamByTutorWithStudents(userRepository.findUserByLogin(login));
        return teams.stream()
                .filter(t -> t.getConfirmed() == TeamState.PENDING)
                .map(this::toPendingTeamDto)
                .collect(Collectors.toList());
    }

    private PendingTeamDto toPendingTeamDto(Team team) {
        PendingTeamDto dto = new PendingTeamDto();
        dto.setId(team.getId());
        dto.setStudents(team.getStudents().stream()
                .map(UserTeam::getStudent)
                .map(UserWithIdDto::new)
                .collect(Collectors.toList()));
        dto.setTopic(team.getTopic());
        dto.setTeamName(team.getTopic());
        dto.setDescription(team.getDescription());

        LOG.info("Found team with id {},", team.getId());

        return dto;
    }

    @Transactional
    public void acceptTeam(long id, String privateToken) {
        Team team = teamRepository.findTeamWithStudents(id);
        team.setConfirmed(TeamState.ACCEPTED);

        createGitlabProject(team, privateToken);

        LOG.info("Accepting team with id {}", team.getId());
    }

    private void createGitlabProject(Team team, String privateToken) {
        LOG.info("Creating gitlab project for team with id {}", team.getId());

        long courseId = team.getProject().getCourse().getId();
        int groupId = courseRepository.findOne(courseId).getGroupId();
        String name = team.getTutor().getLogin() + '_' + team.getId();
        ProjectDto dto = gitLabApi.createProject(privateToken, team.getTopic(), groupId, name);
        team.setGitlabId(dto.getId());
        team.setGitlabPage(dto.getPath());
        setUsersForProject(team, privateToken);
    }

    private void setUsersForProject(Team team, String privateToken) {
        LOG.info("Adding users to gitlab from team {}", team.getId());

        List<Integer> usersId = team.getStudents().stream().map(UserTeam::getStudent)
                .map(User::getGitlabId)
                .collect(Collectors.toList());
        gitLabApi.addUsersToProject(usersId, privateToken, team.getGitlabId());
    }

    @Transactional
    public void removeStudent(long teamId, long userId, String privateToken) {
        LOG.info("Removing student {} from team {}", userId, teamId);

        Team team = teamRepository.findById(teamId);
        User student = userRepository.findOne(userId);
        UserTeam userTeam = userTeamRepository.findUserTeamByTeamAndStudent(team, student);
        gitLabApi.removeUserFromProject(student.getGitlabId(), privateToken, team.getGitlabId());
        userTeamRepository.delete(userTeam);
    }

    @Transactional
    public void changeTopic(long teamId, String topic) {
        Team team = teamRepository.findById(teamId);
        team.setTopic(topic);
        teamRepository.save(team);
    }

    @Transactional
    public void changeDescription(long teamId, String description) {
        Team team = teamRepository.findById(teamId);
        team.setDescription(description);
        teamRepository.save(team);
    }

    @Transactional
    public void changePoints(long teamId, int points) {
        Team team = teamRepository.findById(teamId);
        team.setPoints(points);
        teamRepository.save(team);
    }


    @Transactional
    public List<UserWithIdDto> findFreeStudents(long teamId) {
        Long id = teamRepository.findById(teamId).getProject().getId();
        Project project = projectRepository.findProjectWithTeams(id);

        LOG.info("Loading free students for project {}", project.getId());
        List<Team> teams = project.getTeams();
        return loadUserWithProjects(project.getId())
                .stream()
                .filter(u ->
                        u.getTeamsAsStudent().stream()
                                .map(UserTeam::getTeam)
                                .noneMatch(teams::contains))
                .map(UserWithIdDto::new)
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

    @Transactional
    public void addStudent(UserWithIdDto studentDto, long teamId, String privateToken) {
        LOG.info("Adding student {} to team {}", studentDto, teamId);

        Team team = teamRepository.findById(teamId);
        User student = userRepository.findOne(studentDto.getId());

        LOG.info("deleting previous UserTeams for student {}", student.getId());
        List<UserTeam> userTeams = userTeamRepository.findAllByStudent(student);
        userTeamRepository.delete(userTeams);

        gitLabApi.addUsersToProject(Lists.newArrayList(student.getGitlabId()), privateToken, team.getGitlabId());

        UserTeam userTeam = new UserTeam();
        userTeam.setStudent(student);
        userTeam.setTeam(team);
        userTeam.setConfirmed(true);

        userTeamRepository.save(userTeam);
    }
}
