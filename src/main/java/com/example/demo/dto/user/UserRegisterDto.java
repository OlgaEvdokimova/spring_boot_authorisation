package com.example.demo.dto.user;

import com.example.demo.validation.PasswordValidator;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRegisterDto {

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "The name should be from 2 till 50 characters")
    private String firstName;

    @NotBlank(message = "Lastname cannot be null")
    @Size(min = 2, max = 50, message = "The lastName should be from 2 till 50 characters")
    private String lastName;

    @PasswordValidator
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank
    @Size(min = 8, max = 100)
    private String email;


    @Size(min = 1, max = 20)
    private List<String> phones;
}
