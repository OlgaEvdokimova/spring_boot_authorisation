package com.example.demo.mapper;


import com.example.demo.dto.role.RoleDto;
import com.example.demo.entity.Role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
