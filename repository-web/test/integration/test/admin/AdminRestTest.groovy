package integration.test.admin

import com.database.entity.Course
import com.database.entity.LoggedUser
import com.database.entity.User
import com.database.repository.CourseRepository
import com.database.repository.LoggedUserRepository
import com.database.repository.UserRepository
import com.services.admin.AdminService
import com.web.rest.admin.AdminRest
import integration.config.IntegrationTest
import integration.config.IntegrationTestConfig
import integration.config.TestBeanConfig
import org.apache.commons.io.IOUtils
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.LocalDateTime

@ContextHierarchy([@ContextConfiguration(classes = [IntegrationTestConfig.class]), @ContextConfiguration(classes = [TestBeanConfig.class])])
@Category(IntegrationTest.class)
class AdminRestTest extends Specification{

    @Autowired
    UserRepository userRepository

    @Autowired
    CourseRepository courseRepository

    @Autowired
    LoggedUserRepository loggedUserRepository

    @Autowired
    @Subject
    AdminService adminService

    def User admin;

    def setup() {
        admin = new User(login: "admin", mail: "test@mail.com", eres: "admin")
        userRepository.save(admin)
    }

    def "should add new user to database"() {
        given:
        String login = "login"
        String name = "imie"
        String file = "IMIE,NAZWISKO,LOGIN,ERES,MAIL,GITLAB_LOGIN,ADMIN,LECTURER,TUTOR,STUDENT\n" +
                name + ",nazwisko," + login + ",eres,mail,login2,true,true,true,true";

        when:
        adminService.createUsers(IOUtils.toInputStream(file), admin.login)

        then:
        User user = userRepository.findUserByLogin(login)
        user.getFirstName() == name
    }

    def "should add new course to database"() {
        given:
        loggedUserRepository.save(new LoggedUser(id: 1L, login: "admin", date: LocalDate.now(), privateToken: "test"))
        String courseName = "course"
        String abbreviation = "COURSE"
        String file = "NAZWA_PRZEDMIOTU,SKROT,ERES_PROWADZACEGO\n" +
                courseName + "," + abbreviation + "," +admin.eres

        when:
        adminService.createCourse(IOUtils.toInputStream(file), admin.login)

        then:
        List<Course> course = courseRepository.findAllCoursesWithLecturerLogin(admin.login)
        course.find {c -> c.courseName == courseName && c.abbreviation == abbreviation} != null
    }
}
