package com.database.repository;

import com.database.entity.Team;
import com.database.entity.User;
import com.database.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long>{
    UserTeam findUserTeamByStudentAndTeam(Team team, User student);
}
