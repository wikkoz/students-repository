package com.gitlab;

import com.gitlab.login.LoginApi;
import com.gitlab.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitLabApiImpl implements GitLabApi {

    @Autowired
    private LoginApi loginApi;

    @Override
    public LoginDto login(String username, String password, String url) {
        return loginApi.login(username, password, url);
    }
}
