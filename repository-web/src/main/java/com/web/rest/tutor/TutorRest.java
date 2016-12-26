package com.web.rest.tutor;

import com.services.login.LoginService;
import com.services.student.UserWithIdDto;
import com.services.tutor.*;
import com.web.configuration.TypeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/tutor")
@PreAuthorize("hasAnyRole('LECTURER', 'TUTOR')")
public class TutorRest {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private LoginService loginService;

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
        String privateToken = loginService.getPrivateToken(principal.getName());
        tutorService.acceptTeam(id, privateToken);
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public TutorTeamResponse teamData(@PathVariable("id") long id) {
        return tutorService.getTeamResponse(id);
    }

    @RequestMapping(value = "/team/{id}/remove/{userId}", method = RequestMethod.POST)
    public void removeStudent(Principal principal, @PathVariable("id") long id, @PathVariable("userId") long userId) {
        String privateToken = loginService.getPrivateToken(principal.getName());
        tutorService.removeStudent(id, userId, privateToken);
    }

    @RequestMapping(value = "/team/{id}/changeTopic", method = RequestMethod.POST)
    public void changeTopic(Principal principal, @RequestBody String topic, @PathVariable("id") long id) {
        tutorService.changeTopic(id, topic);
    }

    @RequestMapping(value = "/team/{id}/changeDescription", method = RequestMethod.POST)
    public void changeDescription(Principal principal, @RequestBody String description, @PathVariable("id") long id) {
        tutorService.changeDescription(id, description);
    }

    @RequestMapping(value = "/team/{id}/students", method = RequestMethod.GET)
    public List<UserWithIdDto> freeStudents(@PathVariable("id") long id) {
       return tutorService.findFreeStudents(id);
    }

    @RequestMapping(value = "/team/{id}/addStudent", method = RequestMethod.POST)
    public void addStudent(Principal user, @PathVariable("id") long id, @RequestBody UserWithIdDto student){
        String privateToken = loginService.getPrivateToken(user.getName());
        tutorService.addStudent(student, id, privateToken);
    }

    @RequestMapping(value = "/team/{id}/changePoints", method = RequestMethod.POST)
    public void changePoints(Principal principal, @RequestBody TypeWrapper<Integer> points, @PathVariable("id") long id) {
        tutorService.changePoints(id, points.getValue());
    }
}
