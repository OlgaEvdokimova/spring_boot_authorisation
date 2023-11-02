package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordResetRequest {

    @Email
    private String email;
    @Pattern(regexp = "^(?=.*\\d) (?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z~`! @#$%^&*()_\\-+={[}\\]|\\:\\;\\\"\\'\\<\\,\\>.?\\/].{8,100}$")
    @NotBlank
    @Size(min = 8, max = 100)
    private String newPassword;
    @Pattern(regexp = "^(?=.*\\d) (?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z~`! @#$%^&*()_\\-+={[}\\]|\\:\\;\\\"\\'\\<\\,\\>.?\\/].{8,100}$")
    @NotBlank
    @Size(min = 8, max = 100)
    private String confirmPassword;
}
