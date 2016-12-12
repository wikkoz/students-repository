package com.web.rest.mail;

import com.services.mail.MailRequest;
import com.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/mail")
public class MailRest {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMail(Principal user, @RequestParam(value = "mailDto") MailRequest mailDto) {
        mailService.sendMail(mailDto);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(Principal user, @RequestParam(value = "mailDto") MailRequest mailDto) {
        mailService.sendMail(mailDto);
    }
}
