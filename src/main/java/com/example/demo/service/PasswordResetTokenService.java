package com.example.demo.service;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.User;
import com.example.demo.handler.TokenExpiredException;
import com.example.demo.handler.NotFoundException;
import com.example.demo.handler.UserNotFoundException;
import com.example.demo.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    public static final String PASSWORD_RESET_TOKEN_IS_EXPIRED = "Password reset token is expired";
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    public User getByResetPasswordToken(String token) {
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("Password reset token not found"));
        if (passwordResetToken.getTokenExpirationTime().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(PASSWORD_RESET_TOKEN_IS_EXPIRED);
        }
        return passwordResetTokenRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new UserNotFoundException("User not found by token"));
    }
}
