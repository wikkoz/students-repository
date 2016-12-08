package com.services.tutor;

import com.database.entity.*;
import com.database.repository.TeamRepository;
import com.database.repository.UserRepository;
import com.services.project.ProjectDeadlineDto;
import com.services.student.UserWithIdDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TutorService {

    private final Logger LOG = LoggerFactory.getLogger(TutorService.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

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

        LOG.info("Found team with id {},", team.getId());

        return dto;
    }

    @Transactional
    public void acceptTeam(long id) {
        Team team = teamRepository.findById(id);
        team.setConfirmed(TeamState.ACCEPTED);

        LOG.info("Accepting team with id {}", team.getId());
    }
}
