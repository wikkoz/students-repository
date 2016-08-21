package com.services.login;

import com.gitlab.GitLabApi;
import com.gitlab.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private GitLabApi gitLabApi;

    public void login(LoginRequest loginRequest){
        LoginDto login = gitLabApi.login(loginRequest.getLogin(), loginRequest.getPassword(),"lol");
    }
}
