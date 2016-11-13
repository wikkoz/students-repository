package com.services.project;

import com.google.common.base.MoreObjects;

import java.io.InputStream;
import java.util.List;

public class ProjectCreationRequest {
    private long courseId;
    private int studentsNumber;
    private int points;
    private List<ProjectDeadlineDto> deadlines;
    private InputStream fileStudentData;
    private InputStream fileTutorData;
    private String privateToken;

    public int getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(int studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<ProjectDeadlineDto> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<ProjectDeadlineDto> deadlines) {
        this.deadlines = deadlines;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public InputStream getFileStudentData() {
        return fileStudentData;
    }

    public void setFileStudentData(InputStream fileStudentData) {
        this.fileStudentData = fileStudentData;
    }

    public InputStream getFileTutorData() {
        return fileTutorData;
    }

    public void setFileTutorData(InputStream fileTutorData) {
        this.fileTutorData = fileTutorData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("courseId", courseId)
                .add("studentsNumber", studentsNumber)
                .add("points", points)
                .add("deadlines", deadlines)
                .add("fileStudentData", fileStudentData)
                .add("fileTutorData", fileTutorData)
                .add("privateToken", privateToken)
                .toString();
    }
}
