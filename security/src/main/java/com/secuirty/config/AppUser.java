package com.secuirty.config;

import com.database.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUser implements UserDetails
{
    private String login;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public AppUser(String login, Collection<Role> roles) {
        this.login = login;
        this.grantedAuthorities = roles.stream()
                .map(Role::getRole)
                .map(this::toGrantedAuthority)
                .collect(Collectors.toList());

    }

    private GrantedAuthority toGrantedAuthority(String role) {
        return () -> role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
