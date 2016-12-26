package com.services.project;

import com.google.common.base.MoreObjects;

public class ProjectRecordsDto {
    private long numberOfStudents;
    private long signedStudents;
    private long unsignedStudents;
    private long waitingStudents;

    public long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public long getSignedStudents() {
        return signedStudents;
    }

    public void setSignedStudents(long signedStudents) {
        this.signedStudents = signedStudents;
    }

    public long getUnsignedStudents() {
        return unsignedStudents;
    }

    public void setUnsignedStudents(long unsignedStudents) {
        this.unsignedStudents = unsignedStudents;
    }

    public long getWaitingStudents() {
        return waitingStudents;
    }

    public void setWaitingStudents(long waitingStudents) {
        this.waitingStudents = waitingStudents;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("numberOfStudents", numberOfStudents)
                .add("signedStudents", signedStudents)
                .add("unsignedStudents", unsignedStudents)
                .add("waitingStudents", waitingStudents)
                .toString();
    }
}
