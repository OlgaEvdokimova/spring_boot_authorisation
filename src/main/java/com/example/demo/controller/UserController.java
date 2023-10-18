package com.example.demo.controller;

import com.example.demo.dto.RequestChangePassword;
import com.example.demo.dto.user.UserDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user", description = "Get user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_USER')")
    public UserDto findUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @Operation(summary = "Get userinfo.", description = "Get user info.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER)")
    public UserDto findInfoAboutYourself() {
        return userService.findInfoAboutYourself();
    }

    @Operation(summary = "Update user", description = "Update user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/updateUser")
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_USER')")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @Operation(summary = "Get users", description = "Get users by firstname and lastname")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> findUsersByFirstnameAndLastname(@RequestParam String firstname, @RequestParam String lastname) {
        return userService.findUsersByFirstnameAndLastname(firstname, lastname);
    }

    @Operation(summary = "Get all users details", description = "Get all users details")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users/details")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> checkUsersDetails() {
        return userService.checkUserDetails();
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@RequestParam List<Long> ids) {
        userService.deleteUser(ids);
    }

    @Operation(summary = "Update password", description = "Update password")
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/user/password/update")
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_USER')")
    public void updateUserPassword(@RequestBody RequestChangePassword requestChangePassword) {
        userService.updatePassword(requestChangePassword);
    }
}
