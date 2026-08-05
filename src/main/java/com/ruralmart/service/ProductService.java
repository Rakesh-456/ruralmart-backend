package com.ruralmart.service;

import com.ruralmart.dto.ProductRequest;
import com.ruralmart.entity.Product;
import com.ruralmart.enums.Category;
import com.ruralmart.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(Product product);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> searchProductsByCategory(Category category);

    Page<ProductResponse> getProducts(int page, int size);

    List<ProductResponse> getMyShopProducts();
}