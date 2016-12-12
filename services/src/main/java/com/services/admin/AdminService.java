package com.services.admin;

import com.database.entity.Course;
import com.database.entity.Role;
import com.database.entity.User;
import com.database.repository.CourseRepository;
import com.database.repository.LoggedUserRepository;
import com.database.repository.RoleRepository;
import com.database.repository.UserRepository;
import com.gitlab.GitLabApi;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.services.file.File;
import com.services.file.FileService;
import com.services.user.GitLabUserService;
import com.services.user.UserCreateResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GitLabUserService gitlabApiUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GitLabApi gitLabApi;

    private enum UserFile {
        IMIE, NAZWISKO, LOGIN, ERES, MAIL, GITLAB_LOGIN, ADMIN, LECTURER, STUDENT;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(u -> u.name()).collect(Collectors.toList());
        }
    }

    private enum CourseFile {
        NAZWA_PRZEDMIOTU, SKROT, ERES_PROWADZACEGO;

        public static List<String> getAllNames() {
            return Stream.of(values()).map(u -> u.name()).collect(Collectors.toList());
        }
    }

    public void createUsers(InputStream usersData, String login) {
        File usersFile = fileService.getFile(usersData, UserFile.getAllNames());
        List<User> users = fileService.getObjectFromFile(usersFile, newUser());
        Map<String, UserCreateResponse> responses = users.stream()
                .map(u -> createGitlabUser(u, login))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(UserCreateResponse::getLogin, u -> u));
        users.forEach(u -> u.setGitlabId(responses.get(u.getLogin()).getId()));
        saveUsers(users);

    }

    public void createCourse(InputStream courseData, String login) {
        File coursesFile = fileService.getFile(courseData, CourseFile.getAllNames());
        String token = loggedUserRepository.findFirstByLoginOrderByDateDesc(login).getPrivateToken();
        List<Course> courses = fileService.getObjectFromFile(coursesFile, newCourse(token));
        User admin = userRepository.findUserByLogin(login);
        courses.forEach(c -> addLecturerToGroup(c, admin, token));
        saveCourses(courses);
    }

    private void addLecturerToGroup(Course c, User admin, String privateToken) {
        User lecturer = c.getLecturer();
        if(!Objects.equals(c.getLecturer(), admin)) {
            gitLabApi.addUsersToGroup(Lists.newArrayList(lecturer.getGitlabId()), privateToken, c.getGroupId());
        }
    }

    private Function<List<String>, User> newUser() {
        return row -> {
            User user = new User();
            user.setEres(row.get(UserFile.ERES.ordinal()));
            user.setFirstName(row.get(UserFile.IMIE.ordinal()));
            user.setLastName(row.get(UserFile.NAZWISKO.ordinal()));
            user.setGitlabLogin(row.get(UserFile.GITLAB_LOGIN.ordinal()));
            user.setMail(row.get(UserFile.MAIL.ordinal()));
            user.setLogin(row.get(UserFile.LOGIN.ordinal()));
            user.setRoles(getRolesForRow(row));
            return user;
        };
    }

    private Function<List<String>, Course> newCourse(String token) {
        return row -> {
            Course course = new Course();
            course.setAbbreviation(row.get(CourseFile.SKROT.ordinal()));
            course.setCourseName(row.get(CourseFile.NAZWA_PRZEDMIOTU.ordinal()));
            course.setSemester(getSemester());
            course.setGroupId(gitLabApi.createGroup(token, course.getAbbreviation(), course.getSemester()));
            User lectuer = userRepository.findUserByEres(row.get(CourseFile.ERES_PROWADZACEGO.ordinal()));
            course.setLecturer(lectuer);
            return course;
        };
    }

    private UserCreateResponse createGitlabUser(User user, String login) {
        if (!Strings.isNullOrEmpty(user.getGitlabLogin())) {
            return gitlabApiUser.createUser(user, login);
        }
        return null;
    }

    private List<Role> getRolesForRow(List<String> row) {
        List<Role> roles = Lists.newArrayList();
        for (int i = UserFile.ADMIN.ordinal(); i < UserFile.values().length; ++i) {
            if (BooleanUtils.toBoolean(row.get(i))) {
                roles.add(roleRepository.findRoleByRole(UserFile.values()[i].name()));
            }
        }
        return roles;
    }

    private String getSemester() {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear() % 100);
        if (now.getMonthValue() > 2 && now.getMonthValue() < 10) {
            return year + "L";
        }
        return year + "Z";

    }

    @Transactional
    private void saveUsers(List<User> users) {
        LOG.info("saving users {}", users);
        userRepository.save(users);
    }

    @Transactional
    private void saveCourses(List<Course> courses) {
        LOG.info("saving courses {}", courses);
        courseRepository.save(courses);
    }
}