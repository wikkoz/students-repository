package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {
    @Id
    @SequenceGenerator(name = "team_seq", sequenceName = "team_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    private long id;
    private String topic;
    private String name;
    @Enumerated(EnumType.STRING)
    private TeamState confirmed;
    private long gitlabId;
    private String gitlabPage;
    private int points;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor;

    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> students;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public TeamState getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(TeamState confirmed) {
        this.confirmed = confirmed;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public long getGitlabId() {
        return gitlabId;
    }

    public void setGitlabId(long gitlabId) {
        this.gitlabId = gitlabId;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }
}
