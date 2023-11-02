package com.example.demo.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextReader {

    public Authentication getAuthenticationFromSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
