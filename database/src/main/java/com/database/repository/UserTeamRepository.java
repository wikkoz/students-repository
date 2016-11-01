package com.database.repository;

import com.database.entity.Team;
import com.database.entity.User;
import com.database.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long>{
    UserTeam findUserTeamByTeamAndStudent(Team team, User student);

    @Query("select u from Project p left join p.teams t left join t.students u where p.id = :id")
    Set<UserTeam> findAllUserTeamsForProject(@Param("id") long id);
}
