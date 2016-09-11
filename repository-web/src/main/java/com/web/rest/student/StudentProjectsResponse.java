package com.web.rest.student;

import com.google.common.base.MoreObjects;
import com.services.student.StudentsProjectDto;

import java.util.List;

public class StudentProjectsResponse {
    private List<StudentsProjectDto> projects;

    public StudentProjectsResponse() {
    }

    public StudentProjectsResponse(List<StudentsProjectDto> projects) {
        this.projects = projects;
    }

    public List<StudentsProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<StudentsProjectDto> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projects", projects)
                .toString();
    }
}
