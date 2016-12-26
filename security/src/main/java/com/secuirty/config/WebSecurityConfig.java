package com.secuirty.config;

import com.database.repository.UserRepository;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:/repository.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String CSRF_TOKEN_ANGULAR_DEFAULT_HEADER = "X-XSRF-TOKEN";

    @Autowired
    private Environment environment;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserRepository userRepository;

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties properties = new ServiceProperties();
        properties.setSendRenew(false);
        properties.setService("https://localhost:8080/login/cas");
        properties.setAuthenticateAllArtifacts(true);
        return properties;
    }

    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
        return new CasUserDetailsService();
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(new Cas20ServiceTicketValidator("https://merkury.elka.pw.edu.pl/cas"));
        casAuthenticationProvider.setKey("test");
        return casAuthenticationProvider;
    }

    @Bean
    public AuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl("https://merkury.elka.pw.edu.pl/cas/login");
        entryPoint.setServiceProperties(serviceProperties());

        return entryPoint;
    }

    @Bean
    public SessionAuthenticationStrategy sessionStrategy() {
        SessionAuthenticationStrategy sessionStrategy = new SessionFixationProtectionStrategy();
        return sessionStrategy;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        AuthenticationEntryPoint casAEP = casAuthenticationEntryPoint();
        return casAEP;
    }
    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new AuthenticationProvider() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return new UsernamePasswordAuthenticationToken("ADMIN", "ADMIN", Lists.newArrayList(()->"ADMIN"));
//            }
//
//            @Override
//            public boolean supports(Class<?> authentication) {
//                return true;
//            }
//        });
        auth.authenticationProvider(casAuthenticationProvider());
    }

    @Bean
    public CasAuthenticationFilter authenticationFilter() throws Exception {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        AuthenticationManager authenticationManager = authenticationManagerBean();
        filter.setAuthenticationManager(authenticationManager);
        filter.setSessionAuthenticationStrategy(sessionStrategy());

        return filter;
    }

//    @Bean
//    public SingleSignOutFilter singleSignOutFilter() {
//        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix("https://merkury.elka.pw.edu.pl/cas");
//        return singleSignOutFilter;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterAfter(new CsrfHeaderFilter("/"), CsrfFilter.class)
                .csrf().ignoringAntMatchers("/login/cas").csrfTokenRepository(csrfTokenRepository());
//                .and()
//                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CSRF_TOKEN_ANGULAR_DEFAULT_HEADER);
        return repository;
    }

    @Bean
    public Boolean disableSSLValidation() throws Exception {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        return true;
    }
}
