package com.example.demo.parser;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtParser {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public Optional<String> parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
            return Optional.of(headerAuth.substring(7));
        }
        return Optional.empty();
    }
}
