package com.example.demo.config.properties;

import jakarta.mail.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class EmailProperties {

    @Bean
    public Session session() {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", true);
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.host", "smtp.mailtrap.io");
        mailProps.put("mail.smtp.port", "587");
        mailProps.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        return Session.getDefaultInstance(mailProps);
    }



}
