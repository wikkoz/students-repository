package com.gitlab.project;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabAccessLevel;
import org.gitlab.api.models.GitlabProject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectApi {

    public ProjectDto createProject(GitlabAPI gitlab, String projectName, int namespace, String name) {
        ProjectDto dto = new ProjectDto();
        try {
            GitlabProject project = gitlab.createProject(name, namespace, projectName, null, null, null, null, null, null, null, null);
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

    public void removeUser(GitlabAPI gitlab, Integer userId, Integer projectId) {
        try {
            gitlab.deleteProjectMember(projectId, userId);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot remove user %d from project %d with message %s",
                    userId, projectId, e.getMessage()), e);
        }
    }
}
