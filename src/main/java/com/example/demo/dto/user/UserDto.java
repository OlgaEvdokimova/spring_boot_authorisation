package com.example.demo.dto.user;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phones;
}
