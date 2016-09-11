package com.services.student;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.List;

public class TeamResponse {
    private String confirmed;
    private String gitlabPage;
    private List<String> students;
    private String topic;
    private LocalDate date;

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("confirmed", confirmed)
                .add("gitlabPage", gitlabPage)
                .add("students", students)
                .add("topic", topic)
                .add("date", date)
                .toString();
    }
}
