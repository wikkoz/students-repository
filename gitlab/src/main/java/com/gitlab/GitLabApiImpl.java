package com.gitlab;

import com.gitlab.group.GroupApi;
import com.gitlab.login.LoginApi;
import com.gitlab.login.LoginDto;
import com.gitlab.project.ProjectApi;
import com.gitlab.project.ProjectDto;
import com.gitlab.user.UserApi;
import com.gitlab.user.UserDto;
import org.gitlab.api.GitlabAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GitLabApiImpl implements GitLabApi {

    @Autowired
    private GroupApi groupApi;

    @Autowired
    private LoginApi loginApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private ProjectApi projectApi;

    @Override
    public LoginDto login(String username, String password) {
        return loginApi.login(username, password);
    }

    @Override
    public int createUser(String private_token, UserDto user) {
        GitlabAPI gitlab = loginApi.connect(private_token);
        return userApi.createUser(gitlab, user);
    }

    @Override
    public ProjectDto createProject(String private_token, String name, int groupId, int userId) {
        GitlabAPI gitlab = loginApi.connect(private_token);
        return projectApi.createProject(gitlab, name, groupId);
    }

    @Override
    public Integer createGroup(String private_token, String course, String semester) {
        GitlabAPI gitlab = loginApi.connect(private_token);
        return groupApi.createGroup(gitlab, course, semester);
    }

    @Override
    public void addUsersToProject(List<Integer> userIds, String privateToken, int projectId) {
        GitlabAPI gitlab = loginApi.connect(privateToken);
        userIds.forEach(i -> projectApi.addUserToProject(gitlab, i, projectId));
    }

    @Override
    public void addUsersToGroup(List<Integer> userIds, String privateToken, int groupId) {
        GitlabAPI gitlab = loginApi.connect(privateToken);
        userIds.forEach(i -> groupApi.addUserToGroup(gitlab, i, groupId));
    }
}
