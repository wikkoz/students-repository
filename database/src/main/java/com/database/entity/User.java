package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String mail;
    private String login;
    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_team", referencedColumnName = "id"))
    private List<Team> teamsAsStundent;
    @ManyToMany
    @JoinTable(name = "role_member", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private List<Role> roles;
    @OneToMany(mappedBy = "tutor")
    private List<Team> teamsAsTutor;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Team> getTeamsAsStundent() {
        return teamsAsStundent;
    }

    public void setTeamsAsStundent(List<Team> teamsAsStundent) {
        this.teamsAsStundent = teamsAsStundent;
    }

    public List<Team> getTeamsAsTutor() {
        return teamsAsTutor;
    }

    public void setTeamsAsTutor(List<Team> teamsAsTutor) {
        this.teamsAsTutor = teamsAsTutor;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
