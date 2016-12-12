package com.gitlab.group;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabAccessLevel;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
}
