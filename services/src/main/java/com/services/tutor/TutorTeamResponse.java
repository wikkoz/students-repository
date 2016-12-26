package com.services.tutor;

import com.google.common.base.MoreObjects;
import com.services.mail.MailAddress;
import com.services.project.ProjectDeadlineDto;
import com.services.student.UserWithIdDto;

import java.util.List;

public class TutorTeamResponse {
    private String gitlabPage;
    private int points;
    private List<UserWithIdDto> students;
    private String topic;
    private List<ProjectDeadlineDto> deadlines;
    private String description;
    private List<MailAddress> mailAddresses;

    public String getGitlabPage() {
        return gitlabPage;
    }

    public void setGitlabPage(String gitlabPage) {
        this.gitlabPage = gitlabPage;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<ProjectDeadlineDto> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<ProjectDeadlineDto> deadlines) {
        this.deadlines = deadlines;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<UserWithIdDto> getStudents() {
        return students;
    }

    public void setStudents(List<UserWithIdDto> students) {
        this.students = students;
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
                .add("gitlabPage", gitlabPage)
                .add("points", points)
                .add("students", students)
                .add("topic", topic)
                .add("deadlines", deadlines)
                .add("description", description)
                .add("mailAddresses", mailAddresses)
                .toString();
    }
}
