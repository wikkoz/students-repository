package com.services.student;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.List;

public class TeamResponse {
    private String teamState;
    private String gitlabPage;
    private List<UserWithIdDto> students;
    private String topic;
    private LocalDate date;
    private int numberOfStudents;
    private boolean confirmedUser;
    private boolean leader;

    public String getTeamState() {
        return teamState;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public String getGitlabPage() {
        return gitlabPage;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
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

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public List<UserWithIdDto> getStudents() {
        return students;
    }

    public void setStudents(List<UserWithIdDto> students) {
        this.students = students;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("teamState", teamState)
                .add("gitlabPage", gitlabPage)
                .add("students", students)
                .add("topic", topic)
                .add("date", date)
                .add("numberOfStudents", numberOfStudents)
                .add("confirmedUser", confirmedUser)
                .add("leader", leader)
                .toString();
    }
}
