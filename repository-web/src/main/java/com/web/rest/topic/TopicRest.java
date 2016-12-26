package com.web.rest.topic;

import com.services.topic.TopicDto;
import com.services.topic.TopicService;
import com.web.rest.student.TopicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/topic")
public class TopicRest {

    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public Set<String> getAllCourses(Principal user) {
        return topicService.findAllCourses(user.getName());
    }

    @RequestMapping(value = "/topics", method = RequestMethod.POST)
    public List<TopicResponse> getAllTopics(Principal user, @RequestBody String courseAbbreviation) {
        return topicService.findAllTopic(user.getName(), courseAbbreviation).stream()
                .map(TopicResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/mark/{id}", method = RequestMethod.POST)
    public void markTopic(Principal user, @PathVariable("id") long id, @RequestBody boolean value) {
        topicService.markTopic(user.getName(), id, value);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public void editTopic(Principal user, @PathVariable("id") long id, @RequestBody TopicDto topic) {
        topicService.editTopic(user.getName(), id, topic);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public void removeTopic(Principal user, @PathVariable("id") long id) {
        topicService.deleteTopic(user.getName(), id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addTopic(Principal user, @RequestBody AddTopicRequest request) {
        topicService.addTopic(user.getName(), request.getTopic(), request.getCourse());
    }
}
