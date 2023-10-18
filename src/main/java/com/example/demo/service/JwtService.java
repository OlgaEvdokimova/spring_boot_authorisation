package com.example.demo.service;

import com.example.demo.entity.TokenDetails;
import com.example.demo.entity.User;
import com.example.demo.properties.JwtTokenProperties;
import com.example.demo.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {
    public static final String ACCESS_TOKEN_NOT_FOUND_TEMPLATE = "Access token %s not found";
    private final JwtTokenProperties jwtTokenProperties;
    private final TokenRepository tokenRepository;

    public boolean validateJwtToken(String jwt) {
        try {
            tokenRepository.findByAccessToken(jwt).orElseThrow(() -> new JwtException(String.format(ACCESS_TOKEN_NOT_FOUND_TEMPLATE, jwt)));
            Jwts.parserBuilder()
                    .setSigningKey(getJwtSecret())
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Authentication Error" + e.getMessage());
            throw new RuntimeException("Token is invalid", e);
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
                .signWith(getJwtSecret(), SignatureAlgorithm.HS512)
                .compact();
    }

    private long convertToMs(long expirationTime) {
        return expirationTime * 1000;
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String extractClaim(String jwtToken, Function<Claims, String> claimsResolver) {
        final Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getJwtSecret())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getJwtSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenProperties.getJwtSecret()));
    }
}
