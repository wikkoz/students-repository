package com.gitlab.group;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabAccessLevel;
import org.gitlab.api.models.GitlabUser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GroupApi {
    public Integer createGroup(GitlabAPI gitlab, String course, String semester) {
        try {
            return gitlab.createGroup(course + "_" + semester).getId();
        } catch (IOException e){
            throw new IllegalStateException(String.format("Cannot create group for %s with message %s", course, e.getMessage()), e);
        }
    }

    public void addUserToGroup(GitlabAPI gitlab, int userId, int groupId) {
        try {
            gitlab.addGroupMember(groupId, userId, GitlabAccessLevel.Developer);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot add user %d group %d with message %s",
                    userId, groupId, e.getMessage()), e);
        }
    }

    public Set<Integer> usersInGroup(GitlabAPI gitlabAPI, int groupId) {
        try {
            return gitlabAPI.getGroupMembers(groupId)
                    .stream().map(GitlabUser::getId)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot get users from group %d", groupId));
        }
    }
}
