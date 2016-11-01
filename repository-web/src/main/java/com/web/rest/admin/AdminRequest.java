package com.web.rest.admin;

import com.google.common.base.MoreObjects;
import com.services.file.FileDto;

public class AdminRequest {
    private FileDto userFile;
    private FileDto courseFile;

    public FileDto getUserFile() {
        return userFile;
    }

    public void setUserFile(FileDto userFile) {
        this.userFile = userFile;
    }

    public FileDto getCourseFile() {
        return courseFile;
    }

    public void setCourseFile(FileDto courseFile) {
        this.courseFile = courseFile;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userFile", userFile)
                .add("courseFile", courseFile)
                .toString();
    }
}
