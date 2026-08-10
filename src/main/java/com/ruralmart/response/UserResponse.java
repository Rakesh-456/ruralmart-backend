package com.ruralmart.response;

import com.ruralmart.enums.Role;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;

    public UserResponse() {
    }

    public UserResponse(Long id,
                        String fullName,
                        String email,
                        Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Generate Getters and Setters

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}