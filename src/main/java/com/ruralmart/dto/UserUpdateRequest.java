package com.ruralmart.dto;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}