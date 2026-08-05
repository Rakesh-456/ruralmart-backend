package com.ruralmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShopRequest {

    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @Size(max = 500)
    private String description;

    public ShopRequest() {
    }

    public ShopRequest(String shopName,
                       String phoneNumber,
                       String address,
                       String description) {

            this.shopName = shopName;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.description = description;
    }

    public String getShopName() {
        return shopName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}