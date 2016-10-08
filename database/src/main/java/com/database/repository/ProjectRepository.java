package com.database.repository;

import com.database.entity.Project;
import com.database.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Modifying
    @Query("SELECT p from Project p left join fetch p.teams where p.id =:id")
    Project findProjectWithTeams(@Param("id") long id);
}
