package com.gitlab;

import com.gitlab.login.LoginDto;
import com.gitlab.project.ProjectDto;
import com.gitlab.user.UserDto;

public interface GitLabApi {
    LoginDto login(String username, String password);
    void createUser(String private_token, UserDto user);
    ProjectDto createProject(String private_token, String name, int groupId);
    Integer createGroup(String private_token, String course, String semester);
}
