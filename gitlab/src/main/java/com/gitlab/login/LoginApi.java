package com.gitlab.login;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabSession;
import org.springframework.stereotype.Component;

@Component
public class LoginApi {

    public LoginDto login(String username, String password, String url) {
        LoginDto login = new LoginDto();
        try {
            GitlabSession session = GitlabAPI.connect(username, password, url);
            login.setLogin(username);
            login.setPrivateToken(session.getPrivateToken());
        } catch (Exception e) {

        } finally {
            return login;
        }
    }
}
