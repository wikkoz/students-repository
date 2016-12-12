package com.services.user;

import com.google.common.base.MoreObjects;

public class UserCreateResponse {
    private int id;
    private String password;
    private String login;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                .add("id", id)
                .add("password", password)
                .add("login", login)
                .toString();
    }
}
