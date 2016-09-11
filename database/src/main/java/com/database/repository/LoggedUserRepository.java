package com.database.repository;

import com.database.entity.LoggedUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedUserRepository extends CrudRepository<LoggedUser, Long>{
    Long countByLogin(String login);
    LoggedUser findFirstByLoginOrderByDateDesc(String login);
}
