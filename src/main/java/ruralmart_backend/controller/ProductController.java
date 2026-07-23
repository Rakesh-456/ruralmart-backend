package ruralmart_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruralmart_backend.entity.Product;
import ruralmart_backend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //Dependency Injection
    private final ProductService productService;

    //Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
