package com.services.user;

import com.database.entity.Role;
import com.database.entity.User;
import com.gitlab.GitLabApi;
import com.gitlab.user.UserDto;
import com.services.login.LoginService;
import com.services.mail.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GitLabUserService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private GitLabApi gitlabApi;

    @Autowired
    private MailService mailService;

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
        dto.setAdmin(user.getRoles().contains(Role.ADMIN));
        dto.setCanCreateGroup(user.getRoles().stream().anyMatch(r -> !Objects.equals(r, Role.STUDENT)));
        dto.setProjects(user.getRoles().stream().allMatch(r -> Objects.equals(r, Role.STUDENT)) ? 0 : 9999);
        return dto;
    }
}
