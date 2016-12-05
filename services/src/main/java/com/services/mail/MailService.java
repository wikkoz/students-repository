package com.services.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    public void sendMail(String login, MailRequest request){
        final String username = "wkozakow";
        final String password = "xxxxxxx";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mion.elka.pw.edu.pl");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("wkozakow@mion.elka.pw.edu.pl"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler, No spam to my email, please!");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
