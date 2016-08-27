package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String courseName;
    private String abbreviation;
    @OneToOne
    private User lecturer;
    @OneToMany(mappedBy = "course")
    private List<Project> projects;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
