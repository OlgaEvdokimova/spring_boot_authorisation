package com.example.demo.service;

import com.example.demo.config.properties.JwtTokenProperties;
import com.example.demo.dto.TokenDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProperties jwtTokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtAccessSecret())
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Authentication Error" + e.getMessage());
            throw new IllegalArgumentException("Token is invalid", e);
        }
    }

    public String generateAccessToken(User user) {
        return generateToken(user, jwtTokenProperties.getJwtAccessExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtTokenProperties.getJwtRefreshExpiration());
    }

    public TokenDetails generateTokenDetails(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return TokenDetails.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .user(user)
                .build();
    }

    public String generateToken(UserDetails userDetails, long expirationTime) {
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + convertToMs(expirationTime));
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(getJwtAccessSecret(), SignatureAlgorithm.HS512)
                .compact();
    }

    private long convertToMs(long expirationTime) {
        return expirationTime * 1000;
    }

    public String extractUsernameForAccess(String jwt) {
        return extractClaimForAccess(jwt, Claims::getSubject);
    }

    public String extractUsernameRefresh(String jwt) {
        return extractClaimForRefresh(jwt, Claims::getSubject);
    }

    public String extractClaimForAccess(String jwtToken, Function<Claims, String> claimsResolver) {
        final Claims claims = extractClaimsForAccess(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String extractClaimForRefresh(String jwtToken, Function<Claims, String> claimsResolver) {
        final Claims claims = extractClaimsForRefresh(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaimsForAccess(String jwt) {
        return extractClaims(jwt, getJwtAccessSecret());
    }

    public Claims extractClaimsForRefresh(String jwt) {
        return extractClaims(jwt, getJwtRefreshSecret());
    }

    public Claims extractClaims(String jwt, Key jwtSecret) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getJwtAccessSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenProperties.getJwtSecretAccess()));
    }

    private Key getJwtRefreshSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenProperties.getJwtSecretRefresh()));
    }
}
