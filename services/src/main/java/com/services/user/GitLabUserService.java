package com.services.user;

import com.database.entity.User;
import com.database.repository.UserRepository;
import com.gitlab.GitLabApi;
import com.gitlab.user.UserDto;
import com.google.common.base.Strings;
import com.services.login.LoginService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private GitLabApi gitlabApi;

    public boolean createUser(String login){
        User user = userRepository.findUserByLogin(login);
        UserDto dto = toDto(user);
        String privateToken = loginService.getPrivateToken(login);
        if(Strings.isNullOrEmpty(privateToken)){
            return false;
        }
        gitlabApi.createUser("url", privateToken, dto);
        return true;
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        String password = RandomStringUtils.randomAlphabetic(8);
        dto.setPassword(password);
        dto.setName(user.getName());
        dto.setEmail(user.getMail());
        dto.setUsername(user.getLogin());
        return dto;
    }
}
