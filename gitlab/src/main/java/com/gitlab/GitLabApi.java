package com.gitlab;

import com.gitlab.login.LoginDto;
import com.gitlab.user.UserDto;

public interface GitLabApi {
    LoginDto login(String username, String password, String url);
    void createUser(String url, String privateToken, UserDto user);
}
