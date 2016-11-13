package com.web.rest.project;

import com.services.file.FileService;
import com.services.login.LoginService;
import com.services.project.CourseDto;
import com.services.project.LecturerTeamDto;
import com.services.project.ProjectCreationRequest;
import com.services.project.ProjectService;
import com.web.configuration.TypeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectRest {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectRest.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public List<CourseDto> getProjectsNames(Principal user) {
        String login = user.getName();
        return projectService.findAllCoursesForUser(login);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public List<LecturerTeamDto> getTeamsForProject(@RequestBody TypeWrapper<Long> course) {
        LOG.info("getting teams for project with id {}", course.getValue());
        return projectService.findAllTeamsForCourse(course.getValue());
    }

    @RequestMapping(value = "/createUsers/{courseId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProject(Principal user, @RequestBody ProjectDataDto projectDto,
                              @PathVariable("courseId") long courseId) {
        LOG.info("creating project for course with id {} and request", courseId, projectDto.toString());

        ProjectCreationRequest request = new ProjectCreationRequest();
        String privateToken = loginService.getPrivateToken(user.getName());
        request.setPrivateToken(privateToken);
        request.setFileStudentData(fileService.decodeBase64(projectDto.getStudentFile()));
        request.setFileTutorData(fileService.decodeBase64(projectDto.getTutorFile()));
        request.setStudentsNumber(projectDto.getStudentsNumber());
        request.setDeadlines(projectDto.getDeadlines());
        request.setCourseId(courseId);
        projectService.createProject(request);
    }
}
