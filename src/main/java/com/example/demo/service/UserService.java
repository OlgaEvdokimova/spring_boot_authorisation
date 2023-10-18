package com.example.demo.service;

import com.example.demo.config.security.SecurityContextReader;
import com.example.demo.dto.RequestChangePassword;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.entity.Phone;
import com.example.demo.entity.Role;
import com.example.demo.entity.TokenDetails;
import com.example.demo.entity.User;
import com.example.demo.handler.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.util.Utility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    public static final String USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE = "There is no such user with email: %s";
    public static final String USER_BY_ID_NOT_FOUND_MESSAGE_TEMPLATE = "There is no such user with id: %s";
    public static final String USER_ROLE = "ROLE_USER";
    public static final Long USER_ROLE_ID = 2L;
    public static final String ENTERED_PASSWORD_IS_INCORRECT = "Entered password is incorrect";
    public static final String USERS_IN_SYSTEM_ARE_NOT_MATCHED_WITH_REFRESH_TOKEN = "Users in system are not matched with refresh token!";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    private final SecurityContextReader securityContextReader;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE, email)));
    }

    public User saveUser(UserRegisterDto userRegisterDto) {
        Role userRole = roleRepository.findByName(USER_ROLE).orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userMapper.toUserWithEncodePassword(userRole, userRegisterDto);
        user.getPhoneNumbers().forEach(phone -> phone.setUser(user));
        return userRepository.save(user);
    }

    @Transactional
    public UserDto findById(Long id) {
        User user = getUserById(id);
        return userMapper.toUserDto(user);
    }

    @Transactional
    public UserDto findInfoAboutYourself() {
        User principal = getPrincipal();
        Long id = principal.getId();
        User user = getUserById(id);
        return userMapper.toUserDto(user);
    }

    private User getPrincipal() {
        return (User) securityContextReader.getAuthenticationFromSecurityContext().getPrincipal();
    }

    public void isConfirmedUser(TokenDetails tokenDetails) {
        Authentication authentication = securityContextReader.getAuthenticationFromSecurityContext();
        if (!Objects.equals(tokenDetails.getUser().getEmail(), authentication.getName())) {
            throw new RuntimeException(USERS_IN_SYSTEM_ARE_NOT_MATCHED_WITH_REFRESH_TOKEN);
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_BY_ID_NOT_FOUND_MESSAGE_TEMPLATE, id)));
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                String.format(USER_BY_EMAIL_NOT_FOUND_MESSAGE_TEMPLATE, email)));

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (!userDto.getPhones().isEmpty()) {
            List<Long> phoneIdList = user.getPhoneNumbers().stream()
                    .map(Phone::getPhoneId)
                    .toList();
            phoneRepository.deleteAllById(phoneIdList);
            List<Phone> newPhoneNumbers = userMapper.toPhones(userDto.getPhones());
            newPhoneNumbers.forEach(userPhone -> userPhone.setUser(user));
            user.setPhoneNumbers(newPhoneNumbers);
        }
        return userMapper.toUserDto(user);
    }


    public void updatePassword(User user, String newPassword) {
        String encodedPassword = userMapper.encodePassword(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public List<UserDto> findUsersByFirstnameAndLastname(String firstname, String lastname) {
        Optional<List<User>> users = userRepository.findByFirstnameAndLastname(firstname, lastname);
        return users
                .map(userList -> userList.stream()
                        .map(userMapper::toUserDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public List<UserDto> checkUserDetails() {
        Optional<List<User>> users = userRepository.findByRoleId(USER_ROLE_ID);
        return users
                .map(userList -> userList.stream()
                        .map(userMapper::toUserDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
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
