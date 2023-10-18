package com.example.demo.dto;

import com.example.demo.validation.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

    @Email(message = "Email should be valid")
    @NotBlank
    @Size(min = 8, max = 100)
    private String email;

    @PasswordValidator
    private String password;
}
