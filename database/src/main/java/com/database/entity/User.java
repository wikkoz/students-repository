package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @SequenceGenerator(name = "app_user_seq", sequenceName = "app_user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    private long id;
    private String name;
    private String mail;
    private String login;
    private String eres;

    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private List<Team> teamsAsStudent;

    @ManyToMany
    @JoinTable(name = "role_member", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = "student_project", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> projectsAsStudent;

    @OneToMany(mappedBy = "tutor")
    private List<Team> teamsAsTutor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<Team> getTeamsAsStudent() {
        return teamsAsStudent;
    }

    public void setTeamsAsStundent(List<Team> teamsAsStudent) {
        this.teamsAsStudent = teamsAsStudent;
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

    public void setTeamsAsStudent(List<Team> teamsAsStudent) {
        this.teamsAsStudent = teamsAsStudent;
    }
}
