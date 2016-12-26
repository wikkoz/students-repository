package com.services.project;

import com.google.common.base.MoreObjects;

import java.io.InputStream;
import java.util.List;

public class ProjectCreationRequest {
    private long courseId;
    private int minStudentsNumber;
    private int maxStudentsNumber;
    private int points;
    private List<ProjectDeadlineDto> deadlines;
    private InputStream fileStudentData;
    private InputStream fileTutorData;
    private String privateToken;

    public int getMinStudentsNumber() {
        return minStudentsNumber;
    }

    public void setMinStudentsNumber(int minStudentsNumber) {
        this.minStudentsNumber = minStudentsNumber;
    }

    public int getMaxStudentsNumber() {
        return maxStudentsNumber;
    }

    public void setMaxStudentsNumber(int maxStudentsNumber) {
        this.maxStudentsNumber = maxStudentsNumber;
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

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("courseId", courseId)
                .add("minStudentsNumber", minStudentsNumber)
                .add("maxStudentsNumber", maxStudentsNumber)
                .add("points", points)
                .add("deadlines", deadlines)
                .add("fileStudentData", fileStudentData)
                .add("fileTutorData", fileTutorData)
                .add("privateToken", privateToken)
                .toString();
    }
}
