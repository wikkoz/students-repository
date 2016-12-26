package com.database.repository;

import com.database.entity.Course;
import com.database.entity.Project;
import com.database.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c left join c.lecturer l where l.login = :login")
    List<Course> findAllCoursesWithLecturerLogin(@Param("login") String login);

    @Query("SELECT c FROM Course c left join fetch c.projects p where c.id = :courseId")
    Course findCourseByIdWithProjects(@Param("courseId") long courseId);

    @Query("SELECT t FROM Course c left join c.projects p left join  p.teams t " +
            "where c.id = :courseId")
    List<Team> findTeamsForCourse(@Param("courseId") long courseId);

    @Query("SELECT p FROM Course c left join c.projects p left join fetch p.teams t " +
            "where c.id = :courseId")
    Set<Project> findTeamsWithUsersForCourse(@Param("courseId") long courseId);
}
