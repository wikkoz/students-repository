package com.web.rest.mail;

import com.services.mail.MailRequest;
import com.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/mail")
public class MailRest {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMail(Principal user, @RequestBody MailRequest mailDto) {
        mailService.sendMail(mailDto, user.getName());
    }
}
