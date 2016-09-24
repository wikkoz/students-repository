package com.services.project;

import com.google.common.base.MoreObjects;

import java.io.InputStream;
import java.time.LocalDate;

public class ProjectCreationRequest {
    private String courseName;
    private int studentsNumber;
    private int points;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filename;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
                .add("courseName", courseName)
                .add("studentsNumber", studentsNumber)
                .add("points", points)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("filename", filename)
                .add("fileStudentData", fileStudentData)
                .add("fileTutorData", fileTutorData)
                .add("privateToken", privateToken)
                .toString();
    }
}
