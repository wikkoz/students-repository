package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_sequence")
    @SequenceGenerator(name = "app_user_sequence", sequenceName = "app_user_seq")
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String gitlabLogin;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getEres() {
        return eres;
    }

    public void setEres(String eres) {
        this.eres = eres;
    }

    public List<UserTeam> getTeamsAsStudent() {
        return teamsAsStudent;
    }

    public void setTeamsAsStudent(List<UserTeam> teamsAsStudent) {
        this.teamsAsStudent = teamsAsStudent;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Project> getProjectsAsStudent() {
        return projectsAsStudent;
    }

    public void setProjectsAsStudent(List<Project> projectsAsStudent) {
        this.projectsAsStudent = projectsAsStudent;
    }

    public List<Team> getTeamsAsTutor() {
        return teamsAsTutor;
    }

    public void setTeamsAsTutor(List<Team> teamsAsTutor) {
        this.teamsAsTutor = teamsAsTutor;
    }

    public String name() {
        return firstName + " " + lastName;
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
