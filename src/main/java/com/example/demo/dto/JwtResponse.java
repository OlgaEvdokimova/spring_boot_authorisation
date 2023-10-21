package com.example.demo.dto;

public record JwtResponse (Long userId, String accessToken, String refreshToken) {}

