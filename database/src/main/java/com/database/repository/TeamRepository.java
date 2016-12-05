package com.database.repository;

import com.database.entity.Team;
import com.database.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    Team findById(long id);

    Set<Team> findAllTeamsByTutor(User tutor);

    @Query("select t FROM Team t left join t.project p left join p.course c where c.id = :id")
    List<Team> findAllTeamsFromCourse(@Param("id") long id);

    @Query("SELECT t from Team t left join fetch t.students where t.id = :id")
    Team findTeamWithStudents(@Param("id") long id);

    @Query("SELECT t from Team t left join fetch t.students where t.tutor = :tutor")
    Set<Team> findTeamByTutorWithStudents(@Param("tutor") User tutor);
}
