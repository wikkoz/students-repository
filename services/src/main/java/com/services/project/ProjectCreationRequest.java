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
    private InputStream fileData;
    private String login;

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

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
                .add("fileData", fileData)
                .add("login", login)
                .toString();
    }
}
