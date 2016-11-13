package com.secuirty.config;

import com.database.entity.User;
import com.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CasUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        UserDetails details = (UserDetails) token.getPrincipal();
        String login = details.getUsername();
        String password = details.getPassword();
        User user = userRepository.findUserByLogin(login);
        return new AppUser(login, password,  user.getRoles());
    }
}
