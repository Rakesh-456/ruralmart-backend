package com.ruralmart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import com.ruralmart.enums.Category;
import com.ruralmart.enums.ProductStatus;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@Table(name = "products")
public class    Product {

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Shop shop;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private double price;

    private int stock;

    private String brand;

    private String unit;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public Product() {
    }

    public Product(Long id, String name, String description, Category category,
                   double price, int stock, String brand,
                   String unit, String imageUrl, ProductStatus status, Shop shop) {

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

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}