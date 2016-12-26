package com.web.rest.student;

import com.database.entity.Topic;
import com.google.common.base.MoreObjects;

public class TopicResponse {
    private String topic;
    private long id;
    private boolean chosen;
    private String description;

    public TopicResponse(Topic topic) {
        this.topic = topic.getTopic();
        this.id = topic.getId();
        this.chosen = topic.isChosen();
        this.description = topic.getDescription();
    }

    public TopicResponse(){}

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("id", id)
                .toString();
    }
}
