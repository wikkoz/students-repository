package com.gitlab.project;

import com.gitlab.login.LoginApi;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectApi {

    @Autowired
    private LoginApi loginApi;

    public ProjectDto createProject(GitlabAPI gitlab, String projectName, int namespace) {
        try {
            GitlabProject project = gitlab.createProject(projectName, namespace, null, null, null, null, null, null, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ProjectDto();
    }
}
