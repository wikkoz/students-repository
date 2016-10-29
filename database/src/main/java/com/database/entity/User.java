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

    @OneToMany(mappedBy = "student")
    private List<UserTeam> teamsAsStudent;

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

    public List<UserTeam> getTeamsAsStudent() {
        return teamsAsStudent;
    }

    public void setTeamsAsStundent(List<UserTeam> teamsAsStudent) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!mail.equals(user.mail)) return false;
        if (!login.equals(user.login)) return false;
        return eres != null ? eres.equals(user.eres) : user.eres == null;

    }

    @Override
    public int hashCode() {
        int result = mail.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + (eres != null ? eres.hashCode() : 0);
        return result;
    }
}
