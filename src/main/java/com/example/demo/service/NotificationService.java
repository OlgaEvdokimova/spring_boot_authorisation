package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void sendRegisterNotification(User user) {
        emailService.sendRegistrationEmail(user, TemplateUtil.prepareTemplateInfo(user));
    }

    public void sendDeleteUserNotification(User user) {
        emailService.sendDeleteUserEmail(user, TemplateUtil.prepareTemplateInfo(user));
    }
}
