package com.ruralmart.response;

import com.ruralmart.enums.Category;
import com.ruralmart.enums.ProductStatus;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Category category;
    private double price;
    private int stock;
    private String brand;
    private String unit;
    private String imageUrl;
    private ProductStatus status;
    private ShopSummaryResponse shop;

    public ProductResponse() {
    }

    public ProductResponse(Long id,
                           String name,
                           String description,
                           Category category,
                           double price,
                           int stock,
                           String brand,
                           String unit,
                           String imageUrl,
                           ProductStatus status,
                           ShopSummaryResponse shop) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.unit = unit;
        this.imageUrl = imageUrl;
        this.status = status;
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getUnit() {
        return unit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public ShopSummaryResponse getShop() {
        return shop;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setShop(ShopSummaryResponse shop) {
        this.shop = shop;
    }
}