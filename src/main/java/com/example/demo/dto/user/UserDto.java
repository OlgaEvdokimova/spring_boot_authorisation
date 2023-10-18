package com.example.demo.dto.user;

import com.example.demo.dto.phone.PhoneDto;
import com.example.demo.dto.role.RoleDto;
import com.example.demo.entity.Phone;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private RoleDto roleDto;
    private String email;
    private List<PhoneDto> phones;
}
