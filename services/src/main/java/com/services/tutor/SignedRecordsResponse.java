package com.services.tutor;

import com.google.common.base.MoreObjects;

public class SignedRecordsResponse {
    private String name;
    private long signedTeams;
    private long waitingTeams;
    private long allTeams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSignedTeams() {
        return signedTeams;
    }

    public void setSignedTeams(long signedTeams) {
        this.signedTeams = signedTeams;
    }

    public long getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(long allTeams) {
        this.allTeams = allTeams;
    }

    public long getWaitingTeams() {
        return waitingTeams;
    }

    public void setWaitingTeams(long waitingTeams) {
        this.waitingTeams = waitingTeams;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("signedTeams", signedTeams)
                .add("waitingTeams", waitingTeams)
                .add("allTeams", allTeams)
                .toString();
    }
}
