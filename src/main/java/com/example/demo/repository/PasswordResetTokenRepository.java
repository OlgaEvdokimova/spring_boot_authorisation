package com.example.demo.repository;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query("SELECT prt.user FROM PasswordResetToken prt WHERE prt.token = :token")
    Optional<User> findByResetPasswordToken(String token);

    Optional<PasswordResetToken> findByToken(String token);
}
