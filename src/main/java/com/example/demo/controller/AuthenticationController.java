package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/logIn")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/signUp")
    public JwtResponse register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        return authenticationService.register(userRegisterDto);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Log out",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            description = "Log out from the system")
    public void logout(@RequestParam String refreshToken) {
        authenticationService.logout(refreshToken);
    }
}
