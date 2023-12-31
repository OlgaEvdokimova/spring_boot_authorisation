package com.example.demo.mapper;

import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUserWithEncodePassword(Role userRole, UserRegisterDto userRegisterDto) {
        return User.builder()
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .phoneNumbers(userRegisterDto.getPhones())
                .userRole(userRole)
                .email(userRegisterDto.getEmail())
                .password(encodePassword(userRegisterDto.getPassword()))
                .build();
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

//    public User.UserBuilder getUserBuilder(UserRegisterDto userRegisterDto, Role userRole) {
//        return User.builder()
//                .firstName(userRegisterDto.getFirstName())
//                .lastName(userRegisterDto.getLastName())
//                .phoneNumbers(userRegisterDto.getPhones())
//                .userRole(userRole)
//                .email(userRegisterDto.getEmail());
//    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phones(user.getPhoneNumbers())
                .build();
    }
}
