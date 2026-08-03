package com.ruralmart.controller;

import com.ruralmart.enums.Category;
import com.ruralmart.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.ruralmart.dto.ProductRequest;
import com.ruralmart.entity.Product;
import com.ruralmart.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //Dependency Injection
    private final ProductService service;

    //Constructor Injection
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());

        return service.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        System.out.println("Inside Product Controller");
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @Valid @RequestBody ProductRequest request) {

        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable long id){
       service.deleteProduct(id);
       return "Product deleted Successfully";
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword){
        return service.searchProducts(keyword);
    }

    @GetMapping("/category/{category}")
    public List<Product> searchProductsByCategory(@PathVariable Category category){
        return service.searchProductsByCategory(category);
    }

    @GetMapping("/page")
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getProducts(page, size);
    }

}
