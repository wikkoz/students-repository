package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String topic;
    private String name;
    private boolean confirmed;
    private String gitlabPage;
    private int points;
    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private User tutor;
    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "id_project", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"))
    private List<User> students;

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

    public boolean isState() {
        return confirmed;
    }

    public void setState(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
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
}
