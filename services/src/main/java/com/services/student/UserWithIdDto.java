package com.services.student;

import com.database.entity.User;
import com.database.entity.UserTeam;
import com.google.common.base.MoreObjects;

public class UserWithIdDto {
    private String name;
    private Long id;
    private boolean accepted;

    public UserWithIdDto(User user) {
        name = user.name();
        id = user.getId();
    }

    public UserWithIdDto(UserTeam userTeam) {
        name = userTeam.getStudent().name();
        id = userTeam.getStudent().getId();
        accepted = userTeam.isConfirmed();
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("id", id)
                .add("accepted", accepted)
                .toString();
    }
}
