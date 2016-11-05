package com.web.rest.student;

import com.database.entity.User;
import com.services.student.StudentService;
import com.services.student.StudentsProjectDto;
import com.services.student.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentRest {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public StudentProjectsResponse getProjects(Principal user) {
        String login = user.getName();
        List<StudentsProjectDto> projects = studentService.getProjectsOfStudent(login);
        return new StudentProjectsResponse(projects);
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public TeamResponse getTeam(Principal user, @PathVariable("id") long id) {
        return studentService.getTeamForStudentsId(id, user.getName());
    }


    @RequestMapping(value = "/team/{id}/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentsForProjectResponse> students(@PathVariable("id") long id) {
        List<User> students = studentService.findAllStudentsForTeam(id);
        return students.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/team/{id}/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@PathVariable("id") long id, @RequestParam(value = "studentId") long studentId) {
        studentService.addStudent(id, studentId);
    }

    @RequestMapping(value = "/team/{id}/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable("id") long id, @RequestParam(value = "studentId") long studentId) {
        studentService.deleteStudent(id, studentId);
    }

    @RequestMapping(value = "/team/{id}/topics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TopicResponse> getTopics(@PathVariable("id") long id) {
        return studentService.findTopicsForTeam(id).stream()
                .map(t -> {
                    TopicResponse response = new TopicResponse();
                    response.setId(t.getId());
                    response.setTopic(t.getTopic());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/team/{id}/chooseTopic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void chooseTopic(@PathVariable("id") long id, @RequestParam(value = "topicId") long topicId) {
        studentService.saveTopic(id, topicId);
    }

    @RequestMapping(value = "/team/{id}/accept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptTeam(@PathVariable("id") long id) {
        studentService.acceptTeam(id);
    }

    @RequestMapping(value = "/team/{id}/acceptRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptRequest(@PathVariable("id") long id, Principal user) {
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
