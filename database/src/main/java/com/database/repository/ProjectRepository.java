package com.database.repository;

import com.database.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Query("SELECT p from Project p left join fetch p.teams where p.id =:id")
    Project findProjectWithTeams(@Param("id") long id);
}
