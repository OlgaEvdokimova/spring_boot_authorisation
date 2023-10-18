package com.example.demo.integration;

import com.example.demo.TestPostgresContainer;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
@TestPostgresContainer
public class UserRepositoryTest {

    public static final String EMAIL = "olga@gmal.com";
    @Autowired
    private UserRepository userRepository;

    @Test
    public void expectedTrueIfFounduserByEmail() {
        Optional<User> user = userRepository.findByEmail(EMAIL);
        Assertions.assertTrue(user.isPresent());
    }
}