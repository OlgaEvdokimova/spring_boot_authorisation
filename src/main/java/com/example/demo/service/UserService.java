package com.example.demo.service;

import com.example.demo.config.security.SecurityContextReader;
import com.example.demo.dto.RequestChangePassword;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.handler.NotMatchedException;
import com.example.demo.handler.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {

    public static final String USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE = "There is no such user with email: %s";
    public static final String USER_BY_ID_NOT_FOUND_MESSAGE_TEMPLATE = "There is no such user with id: %s";
    public static final Long USER_ROLE_ID = 2L;
    public static final String ENTERED_PASSWORD_IS_INCORRECT = "Entered password is incorrect";
    public static final String USERS_IN_SYSTEM_ARE_NOT_MATCHED_WITH_REFRESH_TOKEN = "Users in system are not matched with refresh token!";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    private final SecurityContextReader securityContextReader;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE.formatted(email)));
    }

    public User saveUser(UserRegisterDto userRegisterDto) {
        User user = userMapper.toUserWithEncodePassword(Role.USER, userRegisterDto);
        return userRepository.save(user);
    }

    public UserDto findById(Long id) {
        User user = getUserById(id);
        return userMapper.toUserDto(user);
    }

    public UserDto findInfoAboutYourself() {
        User principal = getPrincipal();
        Long id = principal.getId();
        User user = getUserById(id);
        return userMapper.toUserDto(user);
    }

    private User getPrincipal() {
        return (User) securityContextReader.getAuthenticationFromSecurityContext().getPrincipal();
    }

    public void isConfirmedUser(String email) {
        Authentication authentication = securityContextReader.getAuthenticationFromSecurityContext();
        if (!Objects.equals(email, authentication.getName())) {
            throw new NotMatchedException(USERS_IN_SYSTEM_ARE_NOT_MATCHED_WITH_REFRESH_TOKEN);
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_BY_ID_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id)));
    }

    public UserDto updateUser(UserDto userDto) {
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE.formatted(email)));
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (!userDto.getPhones().isEmpty()) {
            user.setPhoneNumbers(userDto.getPhones());
        }
        return userMapper.toUserDto(user);
    }


    public void updatePassword(User user, String newPassword) {
        String encodedPassword = userMapper.encodePassword(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public List<UserDto> findUsersByFirstnameAndLastname(String firstname, String lastname) {
        List<User> users = userRepository.findByFirstnameAndLastname(firstname, lastname);
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public List<UserDto> checkUserDetails() {
        List<User> users = userRepository.findByRoleId(USER_ROLE_ID);
        return users
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public void deleteUser(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        userRepository.deleteAllById(ids);
        users.forEach(notificationService::sendDeleteUserNotification);
    }

    public void updatePassword(RequestChangePassword requestChangePassword) {
        String oldPassword = requestChangePassword.getOldPassword();
        User principal = getPrincipal();
        String newPassword = requestChangePassword.getNewPassword();
        if (BCrypt.checkpw(requestChangePassword.getOldPassword(), principal.getPassword())
                && Utility.isConfirmed(oldPassword, newPassword)) {
            updatePassword(principal, newPassword);
        } else {
            throw new BadCredentialsException(ENTERED_PASSWORD_IS_INCORRECT);
        }
    }
}
