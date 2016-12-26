package com.web.rest.project;

import com.google.common.base.MoreObjects;
import com.services.file.FileDto;
import com.services.project.ProjectDeadlineDto;

import java.util.List;

public class ProjectDataDto {
    private int minStudentsNumber;
    private int maxStudentsNumber;
    private List<ProjectDeadlineDto> deadlines;
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

    public List<ProjectDeadlineDto> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<ProjectDeadlineDto> deadlines) {
        this.deadlines = deadlines;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("minStudentsNumber", minStudentsNumber)
                .add("maxStudentsNumber", maxStudentsNumber)
                .add("deadlines", deadlines)
                .add("tutorFile", tutorFile)
                .add("studentFile", studentFile)
                .toString();
    }
}
