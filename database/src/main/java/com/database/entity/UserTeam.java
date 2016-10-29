package com.database.entity;

import javax.persistence.*;

@Entity(name = "user_team")
public class UserTeam {

    @Id
    @SequenceGenerator(name = "user_team_seq", sequenceName = "user_team_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_team_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private boolean confirmed;
    private boolean leader;

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }
}
