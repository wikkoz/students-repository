package com.web.rest.student;

import com.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentProjectRest {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public StudentProjectsResponse getProjects(String user) {
        return new StudentProjectsResponse();
    }
}
