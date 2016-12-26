package com.services.project;

import com.google.common.base.MoreObjects;
import com.services.mail.MailAddress;

import java.util.List;

public class ProjectTeamResponse {
    private List<LecturerTeamDto> teams;
    private List<MailAddress> mailAddresses;

    public List<LecturerTeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<LecturerTeamDto> teams) {
        this.teams = teams;
    }

    public List<MailAddress> getMailAddresses() {
        return mailAddresses;
    }

    public void setMailAddresses(List<MailAddress> mailAddresses) {
        this.mailAddresses = mailAddresses;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("teams", teams)
                .add("mailAddresses", mailAddresses)
                .toString();
    }
}
