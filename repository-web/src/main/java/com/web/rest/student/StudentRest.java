package com.web.rest.student;

import com.services.student.StudentService;
import com.services.student.StudentsProjectDto;
import com.services.student.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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
        return studentService.getTeamForStudentsId(id);
    }

//    @RequestMapping(value = "/team/{id}/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public void addUser(@PathVariable("id") long id, RequestBody) {
//
//    }
//
//    @RequestMapping(value = "/team/{id}/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public void deleteUser(@PathVariable("id") long id, RequestBody) {
//
//    }
//
//    @RequestMapping(value = "/team/{id}/topic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public void changeTopic(@PathVariable("id") long id, RequestBody) {
//
//    }
//
//    @RequestMapping(value = "/team/{id}/accept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public void accept(@PathVariable("id") long id, RequestBody) {
//
//    }
}
