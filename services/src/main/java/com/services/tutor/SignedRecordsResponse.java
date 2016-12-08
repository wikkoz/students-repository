package com.services.tutor;

import com.google.common.base.MoreObjects;

public class SignedRecordsResponse {
    private String name;
    private long signedTeams;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("signedTeams", signedTeams)
                .add("allTeams", allTeams)
                .toString();
    }
}
