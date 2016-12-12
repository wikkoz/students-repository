package com.gitlab.user;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserApi {

    public int createUser(GitlabAPI gitlab, UserDto user) {
        try {
            GitlabUser gitlabUser = gitlab.createUser(user.getEmail(), user.getPassword(), user.getUsername(), user.getName(),
                    null, null, null, null, null, null, null, null, null, null, null);
            return gitlabUser.getId();
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot create user %s with message %s", user, e.getMessage()), e);
        }
    }
}
