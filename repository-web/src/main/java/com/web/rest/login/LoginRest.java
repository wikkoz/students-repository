package com.web.rest.login;

import com.services.login.LoginRequest;
import com.services.login.LoginService;
import com.web.configuration.TypeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginRest {

    private static final Logger LOG = LoggerFactory.getLogger(LoginRest.class);


    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TypeWrapper<Boolean> loginToGitlab(@RequestBody LoginRestDto login, Principal user) {
        LOG.info("logging user {} to gitlab", user.getName());
        LoginRequest request = new LoginRequest();
        request.setLogin(login.getLogin());
        request.setPassword(login.getPassword());
        return TypeWrapper.of(loginService.login(request, user.getName()));
    }


    @RequestMapping(value = "/logged", method = RequestMethod.GET)
    public TypeWrapper<Boolean> logged(Principal user) {
        return TypeWrapper.of(loginService.isLogged(user.getName()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/user")
    public Map<String, Object> user(Principal user) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", user.getName());
        map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
                .getAuthorities()));
        return map;
    }

}
