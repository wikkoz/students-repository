package com.gitlab;

import com.gitlab.login.LoginApi;
import com.gitlab.login.LoginDto;
import com.gitlab.project.ProjectApi;
import com.gitlab.user.UserApi;
import com.gitlab.user.UserDto;
import org.gitlab.api.GitlabAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/repository.properties")
public class GitLabApiImpl implements GitLabApi {

    @Autowired
    private Environment environment;

    @Autowired
    private LoginApi loginApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private ProjectApi projectApi;

    @Override
    public LoginDto login(String username, String password, String url) {
        return loginApi.login(username, password, url);
    }

    @Override
    public void createUser(String url, String private_token, UserDto user) {
        GitlabAPI gitlab = loginApi.connect(private_token);
        userApi.createUser(gitlab, user);
    }
}
