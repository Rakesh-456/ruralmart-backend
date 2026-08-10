package com.ruralmart.service;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.ResetPasswordRequest;
import com.ruralmart.dto.SendOtpRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.dto.UserUpdateRequest;
import com.ruralmart.response.LoginResponse;
import com.ruralmart.response.UserResponse;
import com.ruralmart.dto.ChangePasswordRequest;

public interface UserService {

    // Step 1 of registration - generates + emails the OTP.
    void sendRegistrationOtp(SendOtpRequest request);

    // Requires a valid, unexpired registration OTP before the User row
    // is created.
    UserResponse register(UserRegistrationRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getMyProfile();

    UserResponse updateMyProfile(UserUpdateRequest request);

    void changePassword(ChangePasswordRequest request);

    // NEW: forgot-password flow, step 1 - emails a reset OTP if the address
    // is registered. Always appears to succeed either way (doesn't reveal
    // whether the email exists - see UserServiceImpl for why).
    void sendPasswordResetOtp(SendOtpRequest request);

    // NEW: forgot-password flow, step 2 - verifies the OTP and sets the
    // new password. No login/JWT required for this endpoint.
    void resetPassword(ResetPasswordRequest request);
}