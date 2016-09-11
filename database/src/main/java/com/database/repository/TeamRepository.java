package com.database.repository;

import com.database.entity.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    Team findById(long id);

    @Modifying
    @Query("select t FROM Team t left join fetch t.project p left join fetch p.course c where c.id = :id")
    List<Team> findAllTeamsFromCourse(@Param("id") long id);
}
