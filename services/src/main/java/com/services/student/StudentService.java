package com.services.student;

import com.database.entity.Team;
import com.database.entity.User;
import com.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Team> getProjectsOfStudent(String login) {
        User student = userRepository.findUserByLogin(login);
        return student.getTeamsAsStundent();
    }
}
