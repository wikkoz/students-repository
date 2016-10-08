package com.database.repository;

import com.database.entity.Course;
import com.database.entity.Topic;
import com.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{
    List<Topic> findTopicsByCourseAndUser(Course course, User user);
}
