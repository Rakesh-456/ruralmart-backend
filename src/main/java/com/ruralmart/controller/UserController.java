package com.ruralmart.controller;

import com.ruralmart.dto.UserUpdateRequest;
import com.ruralmart.response.UserResponse;
import com.ruralmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.ruralmart.dto.ChangePasswordRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserResponse getMyProfile() {
        return userService.getMyProfile();
    }

    @PutMapping("/profile")
    public UserResponse updateMyProfile(
            @Valid @RequestBody UserUpdateRequest request) {

        return userService.updateMyProfile(request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return "Password changed successfully";
    }
}