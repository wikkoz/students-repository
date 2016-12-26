package com.services.login;

import com.database.entity.LoggedUser;
import com.database.entity.User;
import com.database.repository.LoggedUserRepository;
import com.database.repository.UserRepository;
import com.gitlab.GitLabApi;
import com.gitlab.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@PropertySource("classpath:/repository.properties")

public class LoginService {

    @Value("${gitlab.login}")
    private String USERNAME;

    @Value("${gitlab.password}")
    private String PASSWORD;

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    public String logAsAdmin() {
        return gitLabApi.login(USERNAME, PASSWORD).getPrivateToken();
    }

    public String getNameLoggedUser(String login) {
        User logged = userRepository.findUserByLogin(login);
        return logged.name();
    }

    public boolean login(LoginRequest loginRequest, String login) {
        LoginDto dto = gitLabApi.login(loginRequest.getLogin(), loginRequest.getPassword());
        if (dto.isCorrect() && !isLogged(dto.getLogin())) {
            dto.setLogin(login);
            saveCorrectLogin(dto);
        }
        return dto.isCorrect();
    }

    @Transactional
    public boolean isLogged(String login) {
        return loggedUserRepository.countByLogin(login) > 0;
    }

    @Transactional
    public String getPrivateToken(String login){
        if(isLogged(login))
            return loggedUserRepository.findFirstByLoginOrderByDateDesc(login).getPrivateToken();
        return null;
    }

    @Transactional
    private void saveCorrectLogin(LoginDto loginDto) {
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setLogin(loginDto.getLogin());
        loggedUser.setPrivateToken(loginDto.getPrivateToken());
        loggedUser.setDate(LocalDate.now());
        loggedUserRepository.save(loggedUser);
    }
}
