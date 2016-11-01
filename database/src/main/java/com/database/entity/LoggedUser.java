package com.database.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LoggedUser {
    @Id
    @SequenceGenerator(name = "logged_user_seq", sequenceName = "logged_user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logged_user_seq")
    private Long id;
    private String privateToken;
    private LocalDate date;
    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
