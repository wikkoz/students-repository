package com.services.mail;

import com.google.common.base.MoreObjects;

import java.util.List;

public class MailRequest {
    private String text;
    private String topic;
    private List<String> addresses;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
                .add("text", text)
                .add("topic", topic)
                .add("addresses", addresses)
                .toString();
    }
}
