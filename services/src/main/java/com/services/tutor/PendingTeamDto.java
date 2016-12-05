package com.services.tutor;

import com.google.common.base.MoreObjects;
import com.services.student.UserWithIdDto;

import java.util.List;

public class PendingTeamDto {
    private long id;
    private String teamName;
    private List<UserWithIdDto> students;
    private String topic;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<UserWithIdDto> getStudents() {
        return students;
    }

    public void setStudents(List<UserWithIdDto> students) {
        this.students = students;
    }

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
                .add("id", id)
                .add("teamName", teamName)
                .add("students", students)
                .add("topic", topic)
                .toString();
    }
}
