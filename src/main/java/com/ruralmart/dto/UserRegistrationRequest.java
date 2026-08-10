package com.ruralmart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    // OTP entered by the user, sent earlier via /api/auth/register/send-otp.
    @Pattern(regexp = "^\\d{6}$", message = "OTP must be exactly 6 digits")
    private String otp;

    public UserRegistrationRequest() {
    }

    public UserRegistrationRequest(String fullName, String email, String password, String otp) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.otp = otp;
    }

    // Generate Getters and Setters

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getOtp() {
        return otp;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}