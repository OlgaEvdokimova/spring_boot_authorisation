package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;
}
