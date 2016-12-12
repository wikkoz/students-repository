package com.gitlab;

import com.gitlab.login.LoginDto;
import com.gitlab.project.ProjectDto;
import com.gitlab.user.UserDto;

import java.util.List;

public interface GitLabApi {
    LoginDto login(String username, String password);
    int createUser(String private_token, UserDto user);
    ProjectDto createProject(String private_token, String name, int groupId, int userId);
    Integer createGroup(String private_token, String course, String semester);
    void addUsersToProject(List<Integer> userIds, String privateToken, int projectId);
    void addUsersToGroup(List<Integer> userIds, String privateToken, int groupId);
}
