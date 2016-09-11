package com.web.rest.login;

import com.services.login.LoginRequest;
import com.services.login.LoginService;
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

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public boolean loginToGitlab(@RequestBody LoginRestDto login){
        LoginRequest request = new LoginRequest();
        request.setLogin(login.getLogin());
        request.setPassword(login.getPassword());
        return loginService.login(request);
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
