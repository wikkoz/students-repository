package com.database.entity;

import javax.persistence.*;

@Entity
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "topic_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private Long id;
    private String topic;
    private String description;
    private String courseAbbreviation;
    private boolean chosen;

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

    public String getCourseAbbreviation() {
        return courseAbbreviation;
    }

    public void setCourseAbbreviation(String courseAbbreviation) {
        this.courseAbbreviation = courseAbbreviation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isChosen() {
        return chosen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic1 = (Topic) o;

        if (!id.equals(topic1.id)) return false;
        if (topic != null ? !topic.equals(topic1.topic) : topic1.topic != null) return false;
        if (!courseAbbreviation.equals(topic1.courseAbbreviation)) return false;
        return user.equals(topic1.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + courseAbbreviation.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
