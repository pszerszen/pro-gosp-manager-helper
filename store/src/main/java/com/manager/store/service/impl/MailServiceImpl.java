package com.manager.store.service.impl;

import com.manager.store.service.MailService;
import com.manager.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService {

    private static final String SUBJECT = "%s send you a message via Store Manager.";

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Autowired
    @Qualifier("mailSender")
    private JavaMailSenderImpl mailSender;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    public void sendMessage(String messageContent, String from, List<String> to, List<String> cc) {
        sendMessage(messageContent, from, to.toArray(new String[ to.size() ]), cc.toArray(new String[ cc.size() ]));
    }

    @Override
    public void sendMessage(String messageContent, String from, String[] to, String[] cc) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setSubject(String.format(SUBJECT, from));
        message.setTo(to);
        message.setCc(cc);
        message.setSentDate(new Date());
        message.setText(messageContent);

        message.setFrom(from);

        Session session = Session.getInstance(mailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        mailSender.setSession(session);
        mailSender.send(message);
    }

    @Override
    public void sendMessage(String messageContent, Long from, Long[] to, Long[] cc) {
        try {
            String fromMail = retrieveMails(from)[ 0 ];
            sendMessage(messageContent, fromMail, retrieveMails(to), retrieveMails(cc));
        } catch (NullPointerException npe) {
            LOGGER.error("Sender's id is not valid, no such user.", npe);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            LOGGER.error("At least one of the addressee id is not valid", aioobe);
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending an e-mail.", e);
        }
    }

    private String[] retrieveMails(Long... ids) {
        return Arrays.asList(ids).stream()
                .map(id -> userService.get(id).getMail())
                .collect(Collectors.toList()).toArray(new String[ ids.length ]);
    }

    public Properties mailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", Integer.toString(port));
        props.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
        return props;
    }

}
