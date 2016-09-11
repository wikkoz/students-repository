package com.web.rest.project;

import com.google.common.base.MoreObjects;
import com.services.student.StudentsProjectDto;

import java.util.List;

public class ProjectTeamsDto {
    private List<StudentsProjectDto> studentsProjectDto;

    public List<StudentsProjectDto> getStudentsProjectDto() {
        return studentsProjectDto;
    }

    public void setStudentsProjectDto(List<StudentsProjectDto> studentsProjectDto) {
        this.studentsProjectDto = studentsProjectDto;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("studentsProjectDto", studentsProjectDto)
                .toString();
    }
}
