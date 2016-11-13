package com.database.repository;

import com.database.entity.Course;
import com.database.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c left join c.lecturer l where l.login = :login")
    List<Course> findAllCoursesWithLecturerLogin(@Param("login") String login);

    Course findCourseByAbbreviation(String abbreviation);

    @Query("SELECT t FROM Course c left join c.projects p left join  p.teams t " +
            "where c.id = :courseId")
    List<Team> findTeamsForCourse(@Param("courseId") long courseId);
}
