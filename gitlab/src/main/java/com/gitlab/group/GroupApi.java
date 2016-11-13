package com.gitlab.group;

import org.gitlab.api.GitlabAPI;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GroupApi {
    public Integer createGroup(GitlabAPI gitlab, String course, String semester) {
        try {
            return gitlab.createGroup(course + "_" + semester).getId();
        } catch (IOException e){
            throw new IllegalAccessError(String.format("Cannot create group for %s with message %s", course, e.getMessage()));
        }
    }
}
