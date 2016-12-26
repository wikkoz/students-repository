package com.web.rest.project;

import com.services.file.FileService;
import com.services.login.LoginService;
import com.services.mail.MailService;
import com.services.project.*;
import com.web.configuration.TypeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
@PreAuthorize("hasRole('LECTURER')")
public class ProjectRest {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectRest.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public List<CourseDto> getProjectsNames(Principal user) {
        String login = user.getName();
        return projectService.findAllCoursesForUser(login);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public ProjectTeamResponse getTeamsForProject(@RequestBody TypeWrapper<Long> course) {
        LOG.info("getting teams for project with id {}", course.getValue());
        return projectService.getProjectResponse(course.getValue());
    }

    @RequestMapping(value = "/createProject/{courseId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProject(Principal user, @RequestBody ProjectDataDto projectDto,
                              @PathVariable("courseId") long courseId) {
        LOG.info("creating project for course with id {} and request", courseId, projectDto.toString());

        ProjectCreationRequest request = new ProjectCreationRequest();
        request.setFileStudentData(fileService.decodeBase64(projectDto.getStudentFile()));
        request.setFileTutorData(fileService.decodeBase64(projectDto.getTutorFile()));
        request.setMaxStudentsNumber(projectDto.getMaxStudentsNumber());
        request.setMinStudentsNumber(projectDto.getMinStudentsNumber());
        request.setDeadlines(projectDto.getDeadlines());
        request.setCourseId(courseId);
        request.setPrivateToken(loginService.getPrivateToken(user.getName()));
        projectService.createProject(request);
    }

    @RequestMapping(value = "/records/{courseId}", method = RequestMethod.GET)
    public ProjectRecordsDto getProjectsNames(Principal user, @PathVariable("courseId") long courseId) {
        return projectService.getRecordsForCourse(courseId);
    }
}
