package com.example.demo.dto;

import com.example.demo.validation.PasswordValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestChangePassword {

   @PasswordValidator
    private String oldPassword;
    @PasswordValidator
    private String newPassword;
}
