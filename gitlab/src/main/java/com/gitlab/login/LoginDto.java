package com.gitlab.login;

import com.google.common.base.MoreObjects;

public class LoginDto {
    private String privateToken;
    private String login;
    private boolean correct;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("privateToken", privateToken)
                .add("login", login)
                .add("correct", correct)
                .toString();
    }
}
