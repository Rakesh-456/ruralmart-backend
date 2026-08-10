package com.ruralmart.controller;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.SendOtpRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.response.LoginResponse;
import com.ruralmart.response.UserResponse;
import com.ruralmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // NEW: step 1 of registration. Public endpoint - user isn't
    // authenticated yet. Covered by the existing "/api/auth/**" permitAll
    // rule in SecurityConfig, so no security changes were needed.
    @PostMapping("/register/send-otp")
    public Map<String, String> sendOtp(
            @Valid @RequestBody SendOtpRequest request) {

        userService.sendRegistrationOtp(request);
        return Map.of("message", "OTP sent successfully");
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