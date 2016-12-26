package com.services.student;

import com.google.common.base.MoreObjects;
import com.services.mail.MailAddress;
import com.services.project.ProjectDeadlineDto;

import java.util.List;

public class TeamResponse {
    private String teamState;
    private String gitlabPage;
    private List<UserWithIdDto> students;
    private String topic;
    private int points;
    private String description;
    private List<ProjectDeadlineDto> dates;
    private int minNumberOfStudents;
    private int maxNumberOfStudents;
    private boolean confirmedUser;
    private boolean leader;
    private boolean canBeAccepted;
    private List<MailAddress> mailAddresses;

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

    public List<ProjectDeadlineDto> getDates() {
        return dates;
    }

    public void setDates(List<ProjectDeadlineDto> dates) {
        this.dates = dates;
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

    public int getMinNumberOfStudents() {
        return minNumberOfStudents;
    }

    public void setMinNumberOfStudents(int minNumberOfStudents) {
        this.minNumberOfStudents = minNumberOfStudents;
    }

    public int getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public void setMaxNumberOfStudents(int maxNumberOfStudents) {
        this.maxNumberOfStudents = maxNumberOfStudents;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCanBeAccepted() {
        return canBeAccepted;
    }

    public void setCanBeAccepted(boolean canBeAccepted) {
        this.canBeAccepted = canBeAccepted;
    }

    public List<MailAddress> getMailAddresses() {
        return mailAddresses;
    }

    public void setMailAddresses(List<MailAddress> mailAddresses) {
        this.mailAddresses = mailAddresses;
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
                .add("teamState", teamState)
                .add("gitlabPage", gitlabPage)
                .add("students", students)
                .add("topic", topic)
                .add("points", points)
                .add("description", description)
                .add("dates", dates)
                .add("minNumberOfStudents", minNumberOfStudents)
                .add("maxNumberOfStudents", maxNumberOfStudents)
                .add("confirmedUser", confirmedUser)
                .add("leader", leader)
                .add("canBeAccepted", canBeAccepted)
                .add("mailAddresses", mailAddresses)
                .toString();
    }
}
