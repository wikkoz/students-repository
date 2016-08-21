package com.web.rest.login;

import com.services.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginRest {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public void loginToGitlab(@RequestBody LoginRestDto login){

    }

}
