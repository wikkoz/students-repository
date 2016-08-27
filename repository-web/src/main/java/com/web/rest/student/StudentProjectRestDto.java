package com.web.rest.student;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

public class StudentProjectRestDto {
    private String courseName;
    private String projectName;
    private String tutor;
    private LocalDate nextDate;
    private boolean state;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("courseName", courseName)
                .add("projectName", projectName)
                .add("tutor", tutor)
                .add("nextDate", nextDate)
                .add("state", state)
                .toString();
    }
}
