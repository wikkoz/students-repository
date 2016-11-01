package com.database.repository;

import com.database.entity.Role;
import com.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);

    User findUserByEres(String eres);

    List<Role> getRolesByLogin(String login);

    @Query("SELECT s from User s left join fetch s.projectsAsStudent p where p.id =:id")
    Set<User> findUsersForProject(@Param("id") long id);

    @Query("SELECT s from User s left join fetch s.projectsAsStudent p where s.eres =:eres")
    User findUserByEresWithProjects(@Param("eres") String eres);

    @Query("SELECT u from User u left join fetch u.teamsAsStudent where u.id =:id")
    User findUsersWithTeam(@Param("id") long id);
}
