package com.ruralmart.controller;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.ResetPasswordRequest;
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

    // Step 1 of registration. Public endpoint - user isn't authenticated
    // yet. Covered by the existing "/api/auth/**" permitAll rule in
    // SecurityConfig, so no security changes were needed.
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

    // NEW: forgot-password, step 1. Same "/api/auth/**" permitAll rule
    // covers this - a logged-out user obviously can't have a JWT yet.
    @PostMapping("/forgot-password/send-otp")
    public Map<String, String> sendPasswordResetOtp(
            @Valid @RequestBody SendOtpRequest request) {

        userService.sendPasswordResetOtp(request);
        // Always the same message regardless of whether the email exists -
        // see UserServiceImpl.sendPasswordResetOtp for why.
        return Map.of("message", "If that email is registered, a reset code has been sent.");
    }

    // NEW: forgot-password, step 2. Verifies OTP + sets new password.
    // Deliberately does NOT return a JWT - user logs in normally afterwards
    // through the existing /api/auth/login endpoint.
    @PostMapping("/forgot-password/reset")
    public Map<String, String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        userService.resetPassword(request);
        return Map.of("message", "Password reset successfully. You can now log in.");
    }
}