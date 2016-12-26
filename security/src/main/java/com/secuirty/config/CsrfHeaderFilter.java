package com.secuirty.config;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfHeaderFilter extends OncePerRequestFilter {

    private static final String CSRF_TOKEN_ANGULAR_DEFAULT_COOKIE = "XSRF-TOKEN";
    private String contextPath;

    public CsrfHeaderFilter(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, CSRF_TOKEN_ANGULAR_DEFAULT_COOKIE);
            String token = csrf.getToken();
            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                cookie = new Cookie(CSRF_TOKEN_ANGULAR_DEFAULT_COOKIE, token);
                cookie.setPath(contextPath);
                response.addCookie(cookie);
            }
        }
        //(DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST")
        filterChain.doFilter(request, response);
    }
}

