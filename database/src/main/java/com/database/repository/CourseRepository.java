package com.database.repository;

import com.database.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Query("SELECT c.abbreviation FROM Course c left join c.lecturer l where l.login = :login")
    List<String> findAllCoursesWithLecturerLogin(@Param("login") String login);

    Course findCourseByAbbreviation(String abbreviation);
}
