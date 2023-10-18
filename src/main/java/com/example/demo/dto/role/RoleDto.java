package com.example.demo.dto.role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleDto {
    private Long id;
    private String name;
}
