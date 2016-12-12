package com.services.user;

import com.database.entity.User;
import com.gitlab.GitLabApi;
import com.gitlab.user.UserDto;
import com.services.login.LoginService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabUserService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private GitLabApi gitlabApi;

    public UserCreateResponse createUser(User user, String loggedUserLogin){
        UserDto dto = toDto(user);
        UserCreateResponse response = new UserCreateResponse();
        String privateToken = loginService.getPrivateToken(loggedUserLogin);
        response.setId(gitlabApi.createUser(privateToken, dto));
        response.setPassword(dto.getPassword());
        response.setLogin(user.getLogin());
        return response;
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        String password = RandomStringUtils.randomAlphabetic(8);
        dto.setPassword(password);
        dto.setName(user.getLogin());
        dto.setEmail(user.getMail());
        dto.setUsername(user.getLogin());
        return dto;
    }
}
