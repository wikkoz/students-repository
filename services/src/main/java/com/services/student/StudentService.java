package com.services.student;

import com.database.entity.Project;
import com.database.entity.Team;
import com.database.entity.User;
import com.database.repository.TeamRepository;
import com.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<StudentsProjectDto> getProjectsOfStudent(String login) {
        User student = getUserWithTeams(login);
        return student.getTeamsAsStudent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TeamResponse getTeamForStudentsId(long id){
        Team team = getTeam(id);
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setConfirmed(team.getConfirmed().name());
        teamResponse.setGitlabPage(team.getGitlabPage());
        teamResponse.setTopic(team.getTopic());
        List<String> studentNames = team.getStudents()
                .stream().map(User::getName)
                .collect(Collectors.toList());
        teamResponse.setStudents(studentNames);
        return teamResponse;
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
}
