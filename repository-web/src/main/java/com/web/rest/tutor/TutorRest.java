package com.web.rest.tutor;

import com.services.tutor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/tutor")
public class TutorRest {

    @Autowired
    private TutorService tutorService;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public List<TutorProjectResponse> getProject(Principal principal) {
        return tutorService.getAllTeams(principal.getName());
    }

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public List<SignedRecordsResponse> getRecords(Principal principal) {
        return tutorService.getAllRecords(principal.getName());
    }

    @RequestMapping(value = "/newTeams", method = RequestMethod.GET)
    public List<PendingTeamDto> newTeams(Principal principal) {
        return tutorService.getPendingTeams(principal.getName());
    }

    @RequestMapping(value = "/acceptTeam/{id}", method = RequestMethod.POST)
    public void acceptTeam(Principal principal, @PathVariable("id") long id) {
        tutorService.acceptTeam(id);
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public TutorTeamResponse teamData(Principal principal, @PathVariable("id") long id) {
        return tutorService.getTeamResponse(id);
    }
}
