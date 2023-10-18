package com.example.demo.repository;

import com.example.demo.entity.TokenDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenDetails, Long> {

    Optional<TokenDetails> findByRefreshToken(String refresh);

    Optional<TokenDetails> findByAccessToken(String access);

    @Modifying
    @Query("DELETE from TokenDetails td WHERE td.user.id = :userId")
    void deleteTokensByUserId(Long userId);
}
