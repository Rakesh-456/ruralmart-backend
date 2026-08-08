package com.ruralmart.service;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.dto.UserUpdateRequest;
import com.ruralmart.response.LoginResponse;
import com.ruralmart.response.UserResponse;
import com.ruralmart.dto.ChangePasswordRequest;

public interface UserService {

    UserResponse register(UserRegistrationRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getMyProfile();

    UserResponse updateMyProfile(UserUpdateRequest request);

    void changePassword(ChangePasswordRequest request);
}