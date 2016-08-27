package com.web.rest.student;

import com.google.common.base.MoreObjects;

import java.util.List;

public class StudentProjectsResponse {
    private List<StudentProjectRestDto> projects;

    public List<StudentProjectRestDto> getProjects() {
        return projects;
    }

    public void setProjects(List<StudentProjectRestDto> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projects", projects)
                .toString();
    }
}
