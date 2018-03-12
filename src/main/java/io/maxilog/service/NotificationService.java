package io.maxilog.service;

import io.maxilog.entity.Activation;
import io.maxilog.entity.User;
import io.maxilog.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by mossa on 27/11/2017.
 */
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${myHost}")
    private String host;

    @Value("${activationEmail.from}")
    private String from;

    @Value("${activationEmail.subject}")
    private String subject;

    @Value("${activationEmail.text}")
    private String text;


    @Value("${resetPasswordEmail.from}")
    private String from2;

    @Value("${resetPasswordEmail.subject}")
    private String subject2;

    @Value("${resetPasswordEmail.text}")
    private String text2;

    public void sendActivationEmail(User user, Activation activation) throws MessagingException {
        String url = host+"/verify?key="+activation.getKeyActivation();
        sendEmail(user,from,subject,text,url);
    }

    public void sendResetPasswordEmail(User user, String token) throws MessagingException {
        String url = host+"/reset-password?key="+token;
        sendEmail(user,from2,subject2,text2,url);
    }

    public void sendEmail(User eUser, String eFrom, String eSubject, String eText, String url) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setFrom(eFrom);
        helper.setSubject(eSubject);
        helper.setTo(eUser.getEmail());
        String htmlMsg = eText.replace("{name}",eUser.getFirstname()).replace("{link}",url);
        mimeMessage.setContent(htmlMsg, "text/html");
        javaMailSender.send(mimeMessage);

    }
}
