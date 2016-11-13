package com.database.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Long id;
    private int maxPoints;
    private int studentsNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "project")
    private List<Team> teams;

    @ManyToMany
    @JoinTable(name = "student_project", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> students;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectDeadline> deadlines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProjectDeadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<ProjectDeadline> deadlines) {
        this.deadlines = deadlines;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public int getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(int studentsNumber) {
        this.studentsNumber = studentsNumber;
    }
}
