package com.ruralmart.service;

import com.ruralmart.dto.LoginRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.response.LoginResponse;
import com.ruralmart.response.UserResponse;

public interface UserService {

    UserResponse register(UserRegistrationRequest request);

    LoginResponse login(LoginRequest request);
}