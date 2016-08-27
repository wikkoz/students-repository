package com.database.repository;

import com.database.entity.Role;
import com.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    List<Role> getRolesByLogin(String login);
}
