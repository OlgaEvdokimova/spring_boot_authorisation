package com.example.demo.controller;

import com.example.demo.dto.RequestChangePassword;
import com.example.demo.dto.user.UserDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user", description = "Get user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public UserDto findUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @Operation(summary = "Get userinfo.", description = "Get user info.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/info")
    @PreAuthorize("hasRole('USER)")
    public UserDto findInfoAboutYourself() {
        return userService.findInfoAboutYourself();
    }

    @Operation(summary = "Update user", description = "Update user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @Operation(summary = "Get users", description = "Get users by firstname and lastname")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> findUsersByFirstnameAndLastname(@RequestParam String firstname, @RequestParam String lastname) {
        return userService.findUsersByFirstnameAndLastname(firstname, lastname);
    }

    @Operation(summary = "Get all users details", description = "Get all users details")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users/details")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> checkUsersDetails() {
        return userService.checkUserDetails();
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestParam List<Long> ids) {
        userService.deleteUser(ids);
    }

    @Operation(summary = "Update password", description = "Update password")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/user/password/update")
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<Void> updateUserPassword(@RequestBody RequestChangePassword requestChangePassword) {
        userService.updatePassword(requestChangePassword);
        return ResponseEntity.noContent().build();
    }
}
