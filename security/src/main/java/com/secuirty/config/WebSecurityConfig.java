package com.secuirty.config;

import com.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:/repository.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserRepository userRepository;


    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
        return new CasUserDetailsService();
    }

//    @Bean
//    public CasAuthenticationProvider casAuthenticationProvider() {
//        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
//        casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());
////TODO cas
//        return casAuthenticationProvider;
//    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "STUDENT", "TUTOR", "LECTURER");
        auth.inMemoryAuthentication().withUser("student").password("student").roles("STUDENT");
        auth.inMemoryAuthentication().withUser("student2").password("student2").roles("STUDENT");
        auth.inMemoryAuthentication().withUser("tutor").password("tutor").roles("TUTOR");
        auth.inMemoryAuthentication().withUser("lecturer").password("lecturer").roles("TUTOR", "LECTURER");
//        auth.authenticationProvider(casAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
