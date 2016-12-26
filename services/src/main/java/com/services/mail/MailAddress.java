package com.services.mail;

import com.google.common.base.MoreObjects;

import java.util.List;

public class MailAddress {
    private String description;
    private List<String> addresses;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("addresses", addresses)
                .toString();
    }
}
