package com.services.student;

import com.database.entity.User;
import com.google.common.base.MoreObjects;

public class UserWithIdDto {
    private String name;
    private Long id;

    public UserWithIdDto(User user) {
        name = user.name();
        id = user.getId();
    }

    public UserWithIdDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("id", id)
                .toString();
    }
}
