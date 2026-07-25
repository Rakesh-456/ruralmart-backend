package com.ruralmart.service;

import com.ruralmart.dto.ProductRequest;
import com.ruralmart.enums.Category;
import com.ruralmart.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ruralmart.entity.Product;
import com.ruralmart.enums.ProductStatus;
import com.ruralmart.exception.ProductNotFoundException;
import com.ruralmart.repository.ProductRepo;

import java.util.List;

@Service
public class ProductService{

    //Dependency Injection
    private final ProductRepo productRepo;

    //Constructor injection when we use final in dependency injection
    // we have to use constructor injection
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setBrand(product.getBrand());
        response.setUnit(product.getUnit());
        response.setImageUrl(product.getImageUrl());

        return response;
    }


    public Product addProduct(Product product){
        product.setStatus(ProductStatus.ACTIVE);
        return productRepo.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        return mapToResponse(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());

        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        productRepo.delete(product);
    }

    public List<Product> searchProducts(String keyword){
        return productRepo.searchProducts(keyword);
    }

    public List<Product> searchProductsByCategory(Category category) {
        return productRepo.searchByCategory(category);

    }

    //pagination
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }
}