package com.gitlab;

import com.gitlab.login.LoginDto;

public interface GitLabApi {
    LoginDto login(String username, String password, String url);
}
