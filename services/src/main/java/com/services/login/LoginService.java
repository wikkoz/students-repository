package com.services.login;

import com.database.entity.LoggedUser;
import com.database.entity.User;
import com.database.repository.LoggedUserRepository;
import com.database.repository.UserRepository;
import com.gitlab.GitLabApi;
import com.gitlab.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LoginService {

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    public String getNameLoggedUser(String login) {
        User logged = userRepository.findUserByLogin(login);
        return logged.name();
    }

    public boolean login(LoginRequest loginRequest) {
        LoginDto login = gitLabApi.login(loginRequest.getLogin(), loginRequest.getPassword());
        if (login.isCorrect() && !isLogged(login.getLogin()))
            saveCorrectLogin(login);
        return login.isCorrect();
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
