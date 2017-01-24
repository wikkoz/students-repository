package test;

import com.database.entity.Course;
import com.database.entity.Project;
import com.database.entity.Team;
import com.database.entity.User;

import java.util.Collections;

public class TestUtil {
    public static Team initTeam() {
        Course course = new Course();
        course.setGroupId(10);
        course.setAbbreviation("test");
        Project project = new Project();
        project.setCourse(course);
        project.setMaxStudentsNumber(5);
        project.setMinStudentsNumber(3);
        Team team = new Team();
        team.setStudents(Collections.emptyList());
        team.setTutor(new User());
        team.setProject(project);
        return team;
    }
}
