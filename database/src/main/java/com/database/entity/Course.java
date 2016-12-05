package com.database.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    private Long id;
    private int groupId;
    private String courseName;
    private String abbreviation;
    private String semester;

    @OneToOne
    private User lecturer;

    @OneToMany(mappedBy = "course")
    private List<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("courseName", courseName)
                .add("abbreviation", abbreviation)
                .add("semester", semester)
                .toString();
    }
}
