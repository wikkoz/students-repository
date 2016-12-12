package com.services.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@PropertySource("classpath:/repository.properties")
public class MailService {

    @Value("${mail.login}")
    public String USERNAME;

    @Value("${mail.password}")
    public String PASSWORD;

    @Value("${mail.smtp}")
    public String URL;

    @Value("${mail.port}")
    public String PORT;


    public void sendMail(MailRequest request){
        final String username = USERNAME;
        final String password = PASSWORD;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", URL);
        props.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME + "@o2.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("wkozakow@mion.elka.pw.edu.pl"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler, No spam to my email, please.");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
