package com.database.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Team {
    @Id
    @SequenceGenerator(name = "team_seq", sequenceName = "team_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    private Long id;
    private String topic;
    @Enumerated(EnumType.STRING)
    private TeamState confirmed;
    private int gitlabId;
    private String gitlabPage;
    private int points;
    private String description;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor;

    @OneToMany(mappedBy = "team")
    private List<UserTeam> students;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public List<UserTeam> getStudents() {
        return students;
    }

    public void setStudents(List<UserTeam> students) {
        this.students = students;
    }

    public int getGitlabId() {
        return gitlabId;
    }

    public void setGitlabId(int gitlabId) {
        this.gitlabId = gitlabId;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return Objects.equals(id, team.id);

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
