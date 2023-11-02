package com.example.demo.service;

import com.example.demo.dto.TokenDetails;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String TOKEN_WAS_LOGOUT = "Token %s was logout";

    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDetails generateTokenDetails(User user) {
        return jwtService.generateTokenDetails(user);
    }

    public RefreshToken saveRefreshTokenSession(String refreshToken) {
        String id = UUID.randomUUID().toString();
        RefreshToken refreshTokenToSave = RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
        RefreshToken save = refreshTokenRepository.save(refreshTokenToSave);
        return save;
    }

    public void checkUserByToken(String refreshTokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenId)
                .orElseThrow(() -> new JwtException(TOKEN_WAS_LOGOUT.formatted(refreshTokenId)));
        String username = jwtService.extractUsername(refreshToken.getRefreshToken());
        userService.isConfirmedUser(username);
    }
}
