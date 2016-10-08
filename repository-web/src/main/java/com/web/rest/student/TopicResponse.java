package com.web.rest.student;

import com.google.common.base.MoreObjects;

public class TopicResponse {
    private String topic;
    private long id;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("id", id)
                .toString();
    }
}
