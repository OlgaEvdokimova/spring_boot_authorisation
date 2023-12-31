package com.example.demo.config.security;

import com.example.demo.parser.JwtParser;
import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("security")
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            jwtParser.parseJwt(request)
                    .filter(jwtService::validateJwtToken)
                    .ifPresent(jwt -> setJwtAuthenticationToken(request, jwt));
        } catch (Exception e) {
            log.error("Authentication error: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void setJwtAuthenticationToken(HttpServletRequest request, String jwt) {
        String userEmail = jwtService.extractUsernameForAccess(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
