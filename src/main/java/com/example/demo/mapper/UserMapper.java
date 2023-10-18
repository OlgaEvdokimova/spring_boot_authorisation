package com.example.demo.mapper;

import com.example.demo.dto.phone.PhoneDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserRegisterDto;
import com.example.demo.entity.Phone;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
    private final PhoneMapper phoneMapper;

    public User toUserWithEncodePassword(Role userRole, UserRegisterDto userRegisterDto) {
        return getUserBuilder(userRegisterDto, userRole)
                .password(encodePassword(userRegisterDto.getPassword()))
                .build();
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    private User.UserBuilder getUserBuilder(UserRegisterDto userRegisterDto, Role userRole) {
        return User.builder()
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .phoneNumbers(toPhones(userRegisterDto.getPhones()))
                .userRole(userRole)
                .email(userRegisterDto.getEmail());
    }

    public List<Phone> toPhones(List<PhoneDto> phoneDtos) {
        return phoneMapper.toPhones(phoneDtos);
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phones(getPhoneDtos(user.getPhoneNumbers()))
                .roleDto(roleMapper.toRoleDto(user.getUserRole()))
                .build();
    }

    public List<PhoneDto> getPhoneDtos(List<Phone> phones) {
        return phoneMapper.toPhoneDtos(phones);
    }
}
