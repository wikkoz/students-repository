package com.database.repository;

import com.database.entity.ProjectDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDeadlineRepository extends JpaRepository<ProjectDeadline, Long> {
}
