package com.database.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDate nextDate;
    private int maxPoints;
    private String gitlabPage;
    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
