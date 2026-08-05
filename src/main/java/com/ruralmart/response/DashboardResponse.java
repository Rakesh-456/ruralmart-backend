package com.ruralmart.response;

public class DashboardResponse {

    private long totalProducts;
    private long activeProducts;
    private long inactiveProducts;
    private long outOfStockProducts;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalProducts,
                             long activeProducts,
                             long inactiveProducts,
                             long outOfStockProducts) {

        this.totalProducts = totalProducts;
        this.activeProducts = activeProducts;
        this.inactiveProducts = inactiveProducts;
        this.outOfStockProducts = outOfStockProducts;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getActiveProducts() {
        return activeProducts;
    }

    public void setActiveProducts(long activeProducts) {
        this.activeProducts = activeProducts;
    }

    public long getInactiveProducts() {
        return inactiveProducts;
    }

    public void setInactiveProducts(long inactiveProducts) {
        this.inactiveProducts = inactiveProducts;
    }

    public long getOutOfStockProducts() {
        return outOfStockProducts;
    }

    public void setOutOfStockProducts(long outOfStockProducts) {
        this.outOfStockProducts = outOfStockProducts;
    }
}