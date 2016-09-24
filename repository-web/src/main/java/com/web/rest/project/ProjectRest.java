package com.web.rest.project;

import com.services.login.LoginService;
import com.services.project.ProjectCreationRequest;
import com.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectRest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public List<String> getProjectsNames(Principal user) {
        String login = user.getName();
        return projectService.findAllCoursesForUser(login);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public List<String> getTeamsForProject(Principal user, @RequestParam("course") String course) {
        String login = user.getName();
        return projectService.findAllCoursesForUser(login);
    }

    @RequestMapping(value = "/createUsers/{courseName}", headers = "'Content-Type': 'multipart/form-data'", method = RequestMethod.POST)
    public void createProject(Principal user, @RequestParam(value = "file") MultipartFile file, @RequestParam("projectDto") ProjectDataDto projectdto,
            @PathVariable("courseName") String courseName) {
        ProjectCreationRequest request = new ProjectCreationRequest();
        String privateToken = loginService.getPrivateToken(user.getName());
        request.setPrivateToken(privateToken);
        request.setEndDate(projectdto.getEndDate());
        try {
            request.setFileStudentData(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.setFilename(file.getOriginalFilename().toLowerCase());
        request.setPoints(projectdto.getPoints());
        request.setStudentsNumber(projectdto.getStudentsNumber());
        request.setStartDate(projectdto.getStartDate());
        request.setCourseName(courseName);
        projectService.createProject(request);
    }
}
