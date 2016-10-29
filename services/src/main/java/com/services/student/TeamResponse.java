package com.services.student;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.List;

public class TeamResponse {
    private String confirmedTeam;
    private String gitlabPage;
    private List<String> students;
    private String topic;
    private LocalDate date;
    private boolean confirmedUser;

    public String getConfirmedTeam() {
        return confirmedTeam;
    }

    public void setConfirmedTeam(String confirmedTeam) {
        this.confirmedTeam = confirmedTeam;
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

    public boolean isConfirmedUser() {
        return confirmedUser;
    }

    public void setConfirmedUser(boolean confirmedUser) {
        this.confirmedUser = confirmedUser;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("confirmedTeam", confirmedTeam)
                .add("gitlabPage", gitlabPage)
                .add("students", students)
                .add("topic", topic)
                .add("date", date)
                .add("confirmedUser", confirmedUser)
                .toString();
    }
}
