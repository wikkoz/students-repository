package com.services.user;

import com.google.common.base.MoreObjects;

public class UserCreateResponse {
    private boolean success;
    private String password;
    private String gitlabLogin;
    private String login;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGitlabLogin() {
        return gitlabLogin;
    }

    public void setGitlabLogin(String gitlabLogin) {
        this.gitlabLogin = gitlabLogin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("success", success)
                .add("password", password)
                .add("gitlabLogin", gitlabLogin)
                .add("login", login)
                .toString();
    }
}
