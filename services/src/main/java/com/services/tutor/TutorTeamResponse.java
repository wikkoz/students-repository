package com.services.tutor;

import com.google.common.base.MoreObjects;
import com.services.project.ProjectDeadlineDto;
import com.services.student.UserWithIdDto;

import java.util.List;

public class TutorTeamResponse {
    private String gitlabPage;
    private int points;
    private List<UserWithIdDto> students;
    private String topic;
    private List<ProjectDeadlineDto> deadlines;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gitlabPage", gitlabPage)
                .add("points", points)
                .add("students", students)
                .add("topic", topic)
                .add("deadlines", deadlines)
                .toString();
    }
}
