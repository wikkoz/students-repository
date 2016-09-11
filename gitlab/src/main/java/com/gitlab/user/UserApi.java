package com.gitlab.user;

import org.gitlab.api.GitlabAPI;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserApi {

    public void createUser(GitlabAPI gitlab, UserDto user) {
        try {
            gitlab.createUser(user.getEmail(), user.getPassword(), user.getUsername(), user.getName(),
                    null, null, null, null, null, null, null, null, null, null, null);
        } catch (IOException e) {
        }
    }
}
