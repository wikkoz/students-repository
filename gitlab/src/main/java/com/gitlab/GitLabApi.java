package com.gitlab;

import com.gitlab.login.LoginDto;
import com.gitlab.user.UserDto;

public interface GitLabApi {
    LoginDto login(String username, String password);
    void createUser(String private_token, UserDto user);
    void createProject(String private_token, String name, int groupId);
}
