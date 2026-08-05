package com.ruralmart.response;

public class ShopSummaryResponse {

    private Long id;
    private String shopName;

    public ShopSummaryResponse() {
    }

    public ShopSummaryResponse(Long id, String shopName) {
        this.id = id;
        this.shopName = shopName;
    }

    public Long getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}