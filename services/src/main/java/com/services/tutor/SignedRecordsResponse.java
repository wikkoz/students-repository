package com.services.tutor;

import com.google.common.base.MoreObjects;

public class SignedRecordsResponse {
    private String name;
    private int signedStudentsNumber;
    private int studentsNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSignedStudentsNumber() {
        return signedStudentsNumber;
    }

    public void setSignedStudentsNumber(int signedStudentsNumber) {
        this.signedStudentsNumber = signedStudentsNumber;
    }

    public int getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(int studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("signedStudentsNumber", signedStudentsNumber)
                .add("studentsNumber", studentsNumber)
                .toString();
    }
}
