package com.web.rest.admin;

import com.services.admin.AdminService;
import com.services.file.FileDto;
import com.services.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminRest {

    private static final Logger LOG = LoggerFactory.getLogger(AdminRest.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addUsers(Principal user, @RequestBody FileDto usersFile) {
        LOG.info("creating users {} by user {}", usersFile.toString(), user.getName());
        adminService.createUsers(fileService.decodeBase64(usersFile), user.getName());
    }

    @RequestMapping(value = "/courses", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCourses(Principal user, @RequestBody FileDto usersFile) {
        LOG.info("creating courses {} by user {}", usersFile.toString(), user.getName());
        adminService.createCourse(fileService.decodeBase64(usersFile), user.getName());
    }
}
