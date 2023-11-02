//package com.example.demo.unit;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static com.example.demo.integration.AuthenticationControllerTest.DEFAULT_USER;
//import static com.example.demo.integration.UserRepositoryTest.EMAIL;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.only;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void shouldFindByEmail() {
//        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.ofNullable(DEFAULT_USER));
//
//        User user = userService.getUserByEmail(EMAIL);
//
//        then(userRepository).should(only()).findByEmail(eq(EMAIL));
//    }
//}
