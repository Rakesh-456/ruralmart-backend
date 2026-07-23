package ruralmart_backend.service;

import org.springframework.stereotype.Service;
import ruralmart_backend.entity.Product;
import ruralmart_backend.repository.ProductRepo;

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

    public Product addProduct(Product product){
        return productRepo.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
}