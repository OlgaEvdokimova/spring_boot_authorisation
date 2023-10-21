package com.example.demo.controller;

import static com.example.demo.service.UserService.USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE;

import com.example.demo.entity.User;
import com.example.demo.handler.EmailException;
import com.example.demo.handler.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.PasswordResetTokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.Utility;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth/user")
public class PasswordResetController {

    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(ModelMap model) {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String updateResetPasswordToken(HttpServletRequest request, Model model) {
        try {
            String email = request.getParameter("email");
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                    USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE.formatted(email)));
            String passwordToken = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user, passwordToken);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + passwordToken;
            emailService.sendEmailToResetPassword(user, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (EmailException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
        passwordResetTokenService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (Utility.isConfirmed(password, confirmPassword)) {
            User user = passwordResetTokenService.getByResetPasswordToken(token);
            model.addAttribute("title", "Reset your password");
            userService.updatePassword(user, password);
            return "message";
        } else {
            return "failed_message";
        }
    }
}
