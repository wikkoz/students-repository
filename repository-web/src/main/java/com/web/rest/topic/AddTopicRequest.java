package com.web.rest.topic;

import com.google.common.base.MoreObjects;
import com.services.topic.TopicDto;

public class AddTopicRequest {
    private TopicDto topic;
    private String course;

    public TopicDto getTopic() {
        return topic;
    }

    public void setTopic(TopicDto topic) {
        this.topic = topic;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("course", course)
                .toString();
    }
}
