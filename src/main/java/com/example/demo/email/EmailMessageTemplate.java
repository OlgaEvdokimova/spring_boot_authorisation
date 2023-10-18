package com.example.demo.email;

import com.example.demo.handler.EmailException;

import java.io.IOException;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class EmailMessageTemplate {
    private static final String SUBJECT = "Subject should be different for each email in real.";
    public static final String FROM = "senderEmail@gmail.com";

    private final Configuration templateConfiguration;

    public EmailMessageTemplate(
            Configuration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    public MimeMessage prepareMessage(MimeMessage message, Map<String, String> templateInfo, String email, String emailTemplate)
            throws MessagingException, TemplateException, IOException {
        Template template = templateConfiguration.getTemplate(emailTemplate);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateInfo);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject(SUBJECT);
        message.setText(html);
        return message;
    }
}
