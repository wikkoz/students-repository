package com.web.rest.project;

import com.google.common.base.MoreObjects;
import com.services.file.FileDto;

import java.time.LocalDate;

public class ProjectDataDto {
    private int studentsNumber;
    private int points;
    private LocalDate startDate;
    private LocalDate endDate;
    private FileDto tutorFile;
    private FileDto studentFile;
    public FileDto getTutorFile() {
        return tutorFile;
    }

    public void setTutorFile(FileDto tutorFile) {
        this.tutorFile = tutorFile;
    }

    public FileDto getStudentFile() {
        return studentFile;
    }

    public void setStudentFile(FileDto studentFile) {
        this.studentFile = studentFile;
    }

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("studentsNumber", studentsNumber)
                .add("points", points)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("tutorFile", tutorFile)
                .add("studentFile", studentFile)
                .toString();
    }
}
