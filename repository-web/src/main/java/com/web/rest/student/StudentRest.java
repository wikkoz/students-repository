package com.web.rest.student;

import com.database.entity.User;
import com.services.student.StudentService;
import com.services.student.StudentsProjectDto;
import com.services.student.TeamResponse;
import com.web.configuration.TypeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentRest {

    private static final Logger LOG = LoggerFactory.getLogger(StudentRest.class);

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public StudentProjectsResponse getProjects(Principal user) {
        LOG.info("getting all projects for student {}", user.getName());
        String login = user.getName();
        List<StudentsProjectDto> projects = studentService.getProjectsOfStudent(login);
        return new StudentProjectsResponse(projects);
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public TeamResponse getTeam(Principal user, @PathVariable("id") long id) {
        LOG.info("getting team with id {} for student {}", id, user.getName());
        return studentService.getTeamForStudentsId(id, user.getName());
    }


    @RequestMapping(value = "/team/{id}/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentsForProjectResponse> students(@PathVariable("id") long id) {
        LOG.info("getting available students to add for team with id {}", id);

        List<User> students = studentService.findAllStudentsForTeam(id);
        return students.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/team/{id}/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@PathVariable("id") long id, @RequestBody StudentsForProjectResponse student) {
        LOG.info("adding student {} to team with id {}", student.getName(), id);
        studentService.addStudent(id, student.getId());
    }

    @RequestMapping(value = "/team/{id}/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable("id") long id, @RequestBody StudentsForProjectResponse student) {
        LOG.info("removing student {} from team with id {}", student.getName(), id);
        studentService.deleteStudent(id, student.getId());
    }

    @RequestMapping(value = "/team/{id}/topics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TopicResponse> getTopics(@PathVariable("id") long id) {
        LOG.info("getting predefined topics for team with id {}", id);

        return studentService.findTopicsForTeam(id).stream()
                .map(t -> {
                    TopicResponse response = new TopicResponse();
                    response.setId(t.getId());
                    response.setTopic(t.getTopic());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/team/{id}/chooseTopic", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void chooseTopic(@PathVariable("id") long id, @RequestBody TypeWrapper<String> topic) {
        LOG.info("saving topic {} for team with id {}", topic.getValue(), id);
        studentService.saveTopic(id, topic.getValue());
    }

    @RequestMapping(value = "/team/{id}/accept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptTeam(@PathVariable("id") long id) {
        LOG.info("changing state of team for PENDING", id);
        studentService.acceptTeam(id);
    }

    @RequestMapping(value = "/team/{id}/acceptRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptRequest(@PathVariable("id") long id, Principal user) {
        LOG.info("joining to team {} by student", id, user.getName());
        studentService.acceptRequest(id, user.getName());
    }

    @RequestMapping(value = "/team/{id}/takeTeam", method = RequestMethod.POST)
    public void takeTeam(@PathVariable("id") long id, Principal user) {
        studentService.takeTeam(id, user.getName());
    }

    private StudentsForProjectResponse toDto(User user) {
        StudentsForProjectResponse dto = new StudentsForProjectResponse();
        dto.setName(user.name());
        dto.setId(user.getId());
        return dto;
    }
}
