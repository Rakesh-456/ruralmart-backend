package com.ruralmart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.ruralmart.enums.Category;

public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private Category category;

    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Category category, double price, int stock, String brand, String unit, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.unit = unit;
        this.imageUrl = imageUrl;
    }

    // Generate getters and setters

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}