package com.ruralmart.controller;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.response.LoginResponse;
import com.ruralmart.response.UserResponse;
import com.ruralmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(
            @Valid @RequestBody UserRegistrationRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}