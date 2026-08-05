package com.ruralmart.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {

    @OneToMany(mappedBy = "shop")
    @JsonManagedReference
    private List<Product> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(length = 500)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private User owner;

    public Shop() {
    }

    public Shop(Long id,
                String shopName,
                String phoneNumber,
                String address,
                String description,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                User owner,List<Product> products) {
        this.id = id;
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.owner = owner;
        this.products = products;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}