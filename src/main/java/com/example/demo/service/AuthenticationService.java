package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.entity.TokenDetails;
import com.example.demo.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;

    public JwtResponse login(LoginRequest loginRequest) {
        authenticate(loginRequest);
        User user = userService.getUserByEmail(loginRequest.getEmail());
        TokenDetails tokenDetails = tokenService.generateTokenDetails(user);
        tokenService.save(tokenDetails);
        return new JwtResponse(user.getId(), tokenDetails.getAccessToken(), tokenDetails.getRefreshToken());
    }

    private void authenticate(LoginRequest loginRequest){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
    }

    public JwtResponse register(UserRegisterDto userRegisterDto) {
        User user = userService.saveUser(userRegisterDto);
        TokenDetails tokenDetails = tokenService.generateTokenDetails(user);
        tokenService.save(tokenDetails);
        notificationService.sendRegisterNotification(user);
        return new JwtResponse(user.getId(), tokenDetails.getAccessToken(), tokenDetails.getRefreshToken());
    }

    @Transactional
    public void logout(String refreshToken) {
        TokenDetails tokenDetails = tokenService.getTokenDetailsByRefresh(refreshToken);
        tokenService.deleteTokensByUser(tokenDetails);
    }
}
