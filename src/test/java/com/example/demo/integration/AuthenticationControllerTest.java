package com.example.demo.integration;

import com.example.demo.DemoApplication;
import com.example.demo.TestPostgresContainer;
import com.example.demo.entity.Role;
import com.example.demo.entity.TokenDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.util.FileReaderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestPostgresContainer
public class AuthenticationControllerTest {

    private final static String DEFAULT_ROLE = "ROLE_USER";
    private final static String DEFAULT_PASSWORD = "passwordP_123#";
    private final static String DEFAULT_EMAIL = "email@gmail.com";
    private final static String DEFAULT_ACCESS_TOKEN = "string";
    private final static String DEFAULT_REFRESH_TOKEN = "string";
    private final static Long DEFAULT_ID = 1L;
    public static final Role ROLE = Role.builder().id(2L).name(DEFAULT_ROLE).build();

    public static final User DEFAULT_USER = User.builder()
            .id(DEFAULT_ID)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .userRole(ROLE)
            .build();
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;



    @Test
    void shouldReturnJwtResponseOnLogin() throws IOException {
        TokenDetails tokenDetails = TokenDetails.builder()
                .user(DEFAULT_USER)
                .accessToken(DEFAULT_ACCESS_TOKEN)
                .refreshToken(DEFAULT_REFRESH_TOKEN)
                .build();
        given(userRepository.findByEmail(DEFAULT_EMAIL)).willReturn(Optional.of(DEFAULT_USER));
        given(jwtService.generateTokenDetails(DEFAULT_USER)).willReturn(tokenDetails);

        String login = FileReaderUtil.readFromJsonFile("json/login_request.json");
        String response = FileReaderUtil.readFromJsonFile("json/jwt_response.json");
        webTestClient
                .post()
                .uri("/auth/user/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(login)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(response);
    }

    @Test
    void shouldReturnBadRequestOnLogin() throws IOException {

        String login = FileReaderUtil.readFromJsonFile("json/login_request_password_not_match_pattern.json");
        webTestClient
                .post()
                .uri("/auth/user/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(login)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}


