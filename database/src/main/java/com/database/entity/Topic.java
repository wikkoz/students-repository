package com.database.entity;

import javax.persistence.*;

@Entity
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "topic_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private long id;
    private String topic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
