package com.example.demo.service;

import com.example.demo.email.EmailMessageTemplate;
import com.example.demo.entity.User;
import com.example.demo.handler.EmailException;
import com.example.demo.util.TemplateUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String REGISTRATION_EMAIL_TEMPLATE = "registration.ftl";
    private static final String DELETE_USER_EMAIL_TEMPLATE = "removing.ftl";
    private static final String RESET_PASSWORD_EMAIL_TEMPLATE = "reset.ftl";

    private final EmailMessageTemplate emailMessageTemplate;
    private final Session session;

    public MimeMessage createMessage() {
        return new MimeMessage(session);
    }

    public void sendRegistrationEmail(User user, Map<String, String> templateInfo) {
        MimeMessage message = createMessage();
        prepareMessageAndSendEmail(user, templateInfo, message, REGISTRATION_EMAIL_TEMPLATE);
        getSendRegistrationEmail();
    }

    public void sendDeleteUserEmail(User user, Map<String, String> templateInfo) {
        MimeMessage message = createMessage();
        prepareMessageAndSendEmail(user, templateInfo, message, DELETE_USER_EMAIL_TEMPLATE);
        getDeleteUserEmail();
    }

    public void sendEmailToResetPassword(User user, String link) {
        Map<String, String> template = TemplateUtil.prepareTemplateInfoResetPassword(user, link);
        MimeMessage message = createMessage();
        prepareMessageAndSendEmail(user, template, message, RESET_PASSWORD_EMAIL_TEMPLATE);
        getResetPasswordEmail();
    }

    public void prepareMessageAndSendEmail(
            User user,
            Map<String, String> templateInfo,
            MimeMessage message,
            String template) {
        try {
        MimeMessage mimeMessage = emailMessageTemplate.prepareMessage(message, templateInfo, user.getEmail(), template);
        //Transport.send(mimeMessage);
        } catch (IOException | TemplateException | MessagingException e) {
            throw new EmailException("Something went wrong with your message in template" + templateInfo);
        }

    }

    private static void getSendRegistrationEmail() {
        log.info("Registration email is sent");
    }
    private static void getDeleteUserEmail() {
        log.info("User is deleted");
    }
    private static void getResetPasswordEmail() {
        log.info("Reset password email is sent");
    }
}
