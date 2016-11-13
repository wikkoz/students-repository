package com.gitlab.project;

import com.google.common.base.MoreObjects;

public class ProjectDto {
    private String path;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("path", path)
                .add("id", id)
                .toString();
    }
}
