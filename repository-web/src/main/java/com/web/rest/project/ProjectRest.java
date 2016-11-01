package com.web.rest.project;

import com.services.file.FileService;
import com.services.login.LoginService;
import com.services.project.ProjectCreationRequest;
import com.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectRest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileService fileService;

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

    @RequestMapping(value = "/createUsers/{courseName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProject(Principal user, @RequestBody ProjectDataDto projectDto,
                              @PathVariable("courseName") String courseName) {
        ProjectCreationRequest request = new ProjectCreationRequest();
        String privateToken = loginService.getPrivateToken(user.getName());
        request.setPrivateToken(privateToken);
        request.setEndDate(projectDto.getEndDate());
        request.setFileStudentData(fileService.decodeBase64(projectDto.getStudentFile()));
        request.setFileTutorData(fileService.decodeBase64(projectDto.getTutorFile()));
        request.setPoints(projectDto.getPoints());
        request.setStudentsNumber(projectDto.getStudentsNumber());
        request.setStartDate(projectDto.getStartDate());
        request.setCourseName(courseName);
        projectService.createProject(request);
    }
}
