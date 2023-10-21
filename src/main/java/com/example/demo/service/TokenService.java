package com.example.demo.service;

import com.example.demo.entity.TokenDetails;
import com.example.demo.entity.User;
import com.example.demo.handler.TokenRefreshNotFoundException;
import com.example.demo.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    public static final String REFRESH_TOKEN_NOT_FOUND_TEMPLATE = "Refresh token %s not found";
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserService userService;

    public TokenDetails generateTokenDetails(User user) {
        return jwtService.generateTokenDetails(user);
    }

    public TokenDetails save(TokenDetails tokenDetails) {
        return tokenRepository.save(tokenDetails);
    }

    public TokenDetails getTokenDetailsByRefresh(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshNotFoundException(
                        REFRESH_TOKEN_NOT_FOUND_TEMPLATE.formatted(refreshToken)));
    }

    public void deleteTokensByUser(TokenDetails tokenDetails) {
        userService.isConfirmedUser(tokenDetails);
        tokenRepository.deleteTokensByUserId(tokenDetails.getUser().getId());
    }
}
