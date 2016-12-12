package com.gitlab.project;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabAccessLevel;
import org.gitlab.api.models.GitlabProject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectApi {

    public ProjectDto createProject(GitlabAPI gitlab, String projectName, int namespace) {
        ProjectDto dto = new ProjectDto();
        try {
            GitlabProject project = gitlab.createProject(projectName, namespace, null, null, null, null, null, null, null, null, null);
            dto.setId(project.getId());
            dto.setPath(project.getWebUrl());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot create team with name %s and namespace %d with message %s",
                    projectName, namespace, e.getMessage()), e);
        }
        return dto;
    }

    public void addUserToProject(GitlabAPI gitlab, int userId, int projectId) {
        try {
            gitlab.addProjectMember(projectId, userId, GitlabAccessLevel.Developer);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot add user  %d to project %d with message %s",
                    userId, projectId, e.getMessage()), e);
        }
    }
}
