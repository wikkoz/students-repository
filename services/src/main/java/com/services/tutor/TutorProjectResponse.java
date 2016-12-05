package com.services.tutor;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

public class TutorProjectResponse {
    private String courseName;
    private long teamId;
    private String topic;
    private LocalDate deadlineDate;
    private String deadlineDescription;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getDeadlineDescription() {
        return deadlineDescription;
    }

    public void setDeadlineDescription(String deadlineDescription) {
        this.deadlineDescription = deadlineDescription;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("courseName", courseName)
                .add("teamId", teamId)
                .add("topic", topic)
                .add("deadlineDate", deadlineDate)
                .add("deadlineDescription", deadlineDescription)
                .toString();
    }
}
