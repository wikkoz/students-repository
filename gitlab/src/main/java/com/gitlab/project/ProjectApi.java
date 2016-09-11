package com.gitlab.project;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectApi {

    public ProjectDto createProject(GitlabAPI gitlab, String projectName) {
        try {
            GitlabProject project = gitlab.createProject(projectName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ProjectDto();
    }
}
