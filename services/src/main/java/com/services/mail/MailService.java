package com.services.mail;

import com.database.entity.Project;
import com.database.entity.Team;
import com.database.entity.User;
import com.database.entity.UserTeam;
import com.database.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:/repository.properties")
public class MailService {

    @Value("${mail.login}")
    private String USERNAME;

    @Value("${mail.password}")
    private String PASSWORD;

    @Value("${mail.smtp}")
    private String URL;

    @Value("${mail.port}")
    private String PORT;

    @Value("${mail.address}")
    private String ADDRESS;

    @Autowired
    private UserRepository userRepository;

    public List<MailAddress> getAddressesForStudent(Team team) {
        List<MailAddress> mailAddresses = Lists.newArrayList();
        mailAddresses.add(toMailAddress("Prowadzący labolatorium", Lists.newArrayList(team.getTutor())));
        mailAddresses.add(toMailAddress("Prowadzący przedmiot", Lists.newArrayList(team.getProject().getCourse().getLecturer())));
        return mailAddresses;
    }

    public List<MailAddress> getAddressesForTutor(Team team) {
        List<MailAddress> mailAddresses = Lists.newArrayList();
        mailAddresses.add(toMailAddress("Zespół", team.getStudents().stream()
                .map(UserTeam::getStudent)
                .collect(Collectors.toList())));
        mailAddresses.add(toMailAddress("Prowadzący labolatorium", Lists.newArrayList(team.getTutor())));
        mailAddresses.add(toMailAddress("Prowadzący przedmiot", Lists.newArrayList(team.getProject().getCourse().getLecturer())));
        return mailAddresses;
    }

    public List<MailAddress> getAddressForSubject(Set<Project> projects) {
        List<MailAddress> mailAddresses = Lists.newArrayList();
        Set<User> students = projects.stream().flatMap(p -> p.getStudents()
                .stream())
                .collect(Collectors.toSet());
        Set<User> tutors = projects.stream()
                .flatMap(p -> p.getTeams().stream())
                .map(Team::getTutor)
                .collect(Collectors.toSet());

        mailAddresses.add(toMailAddress("Prowadzący projekty", Lists.newArrayList(tutors)));
        mailAddresses.add(toMailAddress("Wszyscy studenci", Lists.newArrayList(students)));
        return mailAddresses;

    }

    private MailAddress toMailAddress(String description, List<User> users) {
        MailAddress mailAddress = new MailAddress();
        mailAddress.setDescription(description);
        mailAddress.setAddresses(users.stream()
                .map(User::getMail)
                .collect(Collectors.toList()));
        return mailAddress;
    }

    public void sendMail(MailRequest request, String login) {
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
            Address[] addresses = new Address[request.getAddresses().size()];
            Iterator<String> it = request.getAddresses().listIterator();
            for (int i = 0; i < request.getAddresses().size(); ++i) {
                addresses[i] = new InternetAddress(it.next());
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADDRESS));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(request.getTopic());

            User user = userRepository.findUserByLogin(login);
            message.setText(mailText(request.getText(), user));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String mailText(String text, User user) {
        return text + String.format("\n Wiadomość wysłana od użytkownika %s %s z maila %s",
                user.getFirstName(), user.getLastName(), user.getMail());
    }
}
