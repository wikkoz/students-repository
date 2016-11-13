package com.services.project;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

public class ProjectDeadlineDto {
    private String description;
    private LocalDate date;
    private int points;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("date", date)
                .add("points", points)
                .toString();
    }
}
