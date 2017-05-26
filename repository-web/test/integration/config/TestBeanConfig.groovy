package integration.config

import com.database.config.JpaConfig
import com.gitlab.GitLabApi
import com.services.admin.AdminService
import com.services.file.FileService
import com.services.login.LoginService
import com.services.mail.MailService
import com.services.user.GitLabUserService
import com.web.rest.admin.AdminRest
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(value = [JpaConfig.class])
class TestBeanConfig {

    @Bean
    AdminService adminService() {
        new AdminService()
    }

    @Bean
    FileService fileService() {
        new FileService()
    }

    @Bean
    AdminRest adminRest() {
        new AdminRest()
    }

    @Bean
    GitLabApi gitLabApi() {
        Mockito.mock(GitLabApi.class)
    }

    @Bean
    GitLabUserService gitLabUserService() {
        new GitLabUserService()
    }

    @Bean
    MailService mailService() {
        new MailService()
    }

    @Bean LoginService loginService() {
        new LoginService()
    }
}
