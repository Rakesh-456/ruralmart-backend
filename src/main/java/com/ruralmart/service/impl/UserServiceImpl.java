package com.ruralmart.service.impl;

import com.ruralmart.dto.ChangePasswordRequest;
import com.ruralmart.dto.ResetPasswordRequest;
import com.ruralmart.dto.SendOtpRequest;
import com.ruralmart.dto.UserRegistrationRequest;
import com.ruralmart.entity.User;
import com.ruralmart.enums.Role;
import com.ruralmart.exception.UserAlreadyExistsException;
import com.ruralmart.otp.OtpPurpose;
import com.ruralmart.repository.UserRepository;
import com.ruralmart.response.UserResponse;
import com.ruralmart.security.JwtService;
import com.ruralmart.service.OtpService;
import com.ruralmart.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ruralmart.dto.LoginRequest;
import com.ruralmart.response.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ruralmart.dto.UserUpdateRequest;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    // Handles OTP generation/verification for both registration and
    // password reset (kept separate internally via OtpPurpose). Email-only
    // - phone number plays no part in OTP verification.
    private final OtpService otpService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           OtpService otpService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    // Step 1 of registration - email OTP only.
    @Override
    public void sendRegistrationOtp(SendOtpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        otpService.generateAndSendOtp(request.getEmail(), OtpPurpose.REGISTRATION);
    }

    @Override
    public UserResponse register(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }

        // The User row is only created if this succeeds - an unverified
        // email can never result in a registered account. Throws
        // OtpValidationException (not found / expired / incorrect) and
        // consumes the OTP on success so it can't be replayed.
        otpService.verifyOtp(request.getEmail(), request.getOtp(), OtpPurpose.REGISTRATION);

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getRole()
        );
    }

    // UNCHANGED: OTP is not involved in login at all. Existing JWT flow
    // preserved exactly - verify credentials, generateToken, return.
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Login successful"
        );
    }

    @Override
    public UserResponse getMyProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @Override
    public UserResponse updateMyProfile(UserUpdateRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.getPhoneNumber().equals(request.getPhoneNumber())
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {

            throw new UserAlreadyExistsException("Phone number already registered");
        }

        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getFullName(),
                updatedUser.getEmail(),
                updatedUser.getPhoneNumber(),
                updatedUser.getRole()
        );
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException("Current password is incorrect");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException(
                    "New password and confirm password do not match");
        }

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "New password must be different from current password");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    // Forgot-password, step 1 - email OTP only, phone not involved.
    @Override
    public void sendPasswordResetOtp(SendOtpRequest request) {

        // Deliberately does NOT throw if the email isn't registered - the
        // controller always returns the same generic success message either
        // way, to avoid leaking which emails have accounts (account
        // enumeration). Only actually sends an email when the account is real.
        if (userRepository.existsByEmail(request.getEmail())) {
            otpService.generateAndSendOtp(request.getEmail(), OtpPurpose.PASSWORD_RESET);
        }
    }

    // Forgot-password, step 2. Does NOT issue a JWT - the user logs in
    // normally afterwards through the existing /api/auth/login flow.
    @Override
    public void resetPassword(ResetPasswordRequest request) {

        otpService.verifyOtp(request.getEmail(), request.getOtp(), OtpPurpose.PASSWORD_RESET);

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}