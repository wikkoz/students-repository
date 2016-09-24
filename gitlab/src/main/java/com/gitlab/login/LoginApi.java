package com.gitlab.login;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/repository.properties")

public class LoginApi {

    @Value("${gitlab.url}")
    private String URL;

    public GitlabAPI connect(String privateToken) {
        return GitlabAPI.connect(URL, privateToken);
    }

    public LoginDto login(String username, String password) {
        LoginDto login = new LoginDto();
        try {
            GitlabSession session = GitlabAPI.connect(URL, username, password);
            login.setLogin(username);
            login.setPrivateToken(session.getPrivateToken());
            login.setCorrect(true);
        } catch (Exception e) {
            login.setCorrect(false);
        }
        return login;
    }
}
