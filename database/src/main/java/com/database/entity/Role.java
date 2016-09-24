package com.database.entity;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private long id;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
