package com.services.topic;

import com.database.entity.*;
import com.database.repository.TeamRepository;
import com.database.repository.TopicRepository;
import com.database.repository.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Set<String> findAllCourses(String login) {
        User tutor = userRepository.findUserByLogin(login);
        Set<String> coursesFromTopics =  topicRepository.findTopicsByUser(tutor)
                .stream().map(Topic::getCourseAbbreviation)
                .collect(Collectors.toSet());
        Set<String> currentCourses = teamRepository.findAllTeamsByTutor(tutor).stream()
                .map(Team::getProject)
                .map(Project::getCourse)
                .map(Course::getAbbreviation)
                .collect(Collectors.toSet());
        currentCourses.addAll(coursesFromTopics);
        return currentCourses;
    }

    public List<Topic> findAllTopic(String login, String courseAbbreviation) {
        User tutor = userRepository.findUserByLogin(login);
        return topicRepository.findTopicsByCourseAbbreviationAndUser(courseAbbreviation, tutor);
    }

    public void editTopic(String login, long topicId, TopicDto topicDto) {
        Topic topic = topicRepository.findOne(topicId);
        checkIfUserCanEditTopic(login, topic);
        saveTopic(topic, topicDto);
    }

    public void deleteTopic(String login, long topicId) {
        Topic topic = topicRepository.findOne(topicId);
        checkIfUserCanEditTopic(login, topic);
        deleteTopic(topic);
    }


    public void addTopic(String login, TopicDto topicDto, String course) {
        User tutor = userRepository.findUserByLogin(login);
        Topic topic = new Topic();
        topic.setTopic(topicDto.getTopic());
        topic.setDescription(topicDto.getDescription());
        topic.setUser(tutor);
        topic.setCourseAbbreviation(course);
        addTopic(topic);
    }

    public void markTopic(String login, long topicId, boolean value){
        Topic topic = topicRepository.findOne(topicId);
        checkIfUserCanEditTopic(login, topic);
        markTopic(topic, value);
    }

    private void checkIfUserCanEditTopic(String login, Topic topic) {
        User tutor = userRepository.findUserByLogin(login);
        List<Topic> userTopics = topicRepository.findTopicsByUser(tutor);
        Preconditions.checkArgument(userTopics.contains(topic), new IllegalAccessError(
                String.format("User with login %s doesn't have access to topic with id %d,", login, topic.getId())
        ));
    }

    @Transactional
    private void saveTopic(Topic topic, TopicDto topicDto) {
        topic.setTopic(topicDto.getTopic());
        topic.setDescription(topicDto.getDescription());
        topicRepository.save(topic);
    }


    @Transactional
    private void markTopic(Topic topic, boolean value) {
        topic.setChosen(value);
        topicRepository.save(topic);
    }

    @Transactional
    private void deleteTopic(Topic topic) {
        topicRepository.delete(topic);
    }

    @Transactional
    private void addTopic(Topic topic) {
        topicRepository.save(topic);
    }
}
