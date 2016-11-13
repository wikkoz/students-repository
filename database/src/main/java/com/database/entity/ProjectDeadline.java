package com.database.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ProjectDeadline {

    @Id
    @SequenceGenerator(name = "project_deadline_seq", sequenceName = "project_deadline_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_deadline_seq")
    private Long id;

    private LocalDate date;
    private int points;
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
