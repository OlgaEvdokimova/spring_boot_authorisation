package com.example.demo.config.token;

import com.example.demo.properties.JwtTokenProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt.token")
    public JwtTokenProperties jwtTokenProperties() {
        return new JwtTokenProperties();
    }
}
