package com.services.login;

import com.database.entity.LoggedUser;
import com.database.repository.LoggedUserRepository;
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
    private LoggedUserRepository loggedUserRepository;

    public boolean login(LoginRequest loginRequest) {
        LoginDto login = gitLabApi.login(loginRequest.getLogin(), loginRequest.getPassword(), "lol");
        if (login.isCorrect() && isNotLogged(login))
            saveCorrectLogin(login);
        return login.isCorrect();
    }

    @Transactional
    private boolean isNotLogged(LoginDto loginDto) {
        return loggedUserRepository.countByLogin(loginDto.getLogin()) == 0;
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
