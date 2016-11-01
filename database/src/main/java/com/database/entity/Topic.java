package com.database.entity;

import javax.persistence.*;

@Entity
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "topic_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private Long id;
    private String topic;

    @OneToOne
    private Course course;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
