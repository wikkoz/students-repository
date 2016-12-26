package com.database.entity;

import javax.persistence.*;

@Entity
public class Role {

    public final static Role ADMIN;
    public final static Role STUDENT;
    public final static Role LECTURER;
    public final static Role TUTOR;

    static {
        ADMIN = new Role();
        ADMIN.setRole("ROLE_ADMIN");

        STUDENT = new Role();
        STUDENT.setRole("ROLE_STUDENT");

        LECTURER = new Role();
        LECTURER.setRole("ROLE_LECTURER");

        TUTOR = new Role();
        TUTOR.setRole("ROLE_TUTOR");
    }

    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        return role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
