package com.ruralmart.service.impl;

import com.ruralmart.dto.ProductRequest;
import com.ruralmart.entity.Product;
import com.ruralmart.entity.Shop;
import com.ruralmart.entity.User;
import com.ruralmart.enums.Category;
import com.ruralmart.enums.ProductStatus;
import com.ruralmart.exception.ProductNotFoundException;
import com.ruralmart.exception.UnauthorizedProductAccessException;
import com.ruralmart.repository.ProductRepo;
import com.ruralmart.repository.ShopRepository;
import com.ruralmart.repository.UserRepository;
import com.ruralmart.response.ProductResponse;
import com.ruralmart.response.ShopSummaryResponse;
import com.ruralmart.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public ProductServiceImpl(ProductRepo productRepo,
                              UserRepository userRepository,
                              ShopRepository shopRepository) {

        this.productRepo = productRepo;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
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
        response.setStatus(product.getStatus());

        ShopSummaryResponse shopResponse =
                new ShopSummaryResponse(
                        product.getShop().getId(),
                        product.getShop().getShopName()
                );

        response.setShop(shopResponse);

        return response;
    }

    @Override public ProductResponse addProduct(Product product) {
        Shop shop = getCurrentUserShop();
        product.setShop(shop);
        product.setStatus(ProductStatus.ACTIVE);
        Product savedProduct = productRepo.save(product);
        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id,
                                         ProductRequest request) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        Shop currentUserShop = getCurrentUserShop();

        if (!product.getShop().getId().equals(currentUserShop.getId())) {
            throw new UnauthorizedProductAccessException(
                    "You cannot update another shop's product");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());

        Product updatedProduct = productRepo.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id) .orElseThrow(() ->
                new ProductNotFoundException( "Product not found with id : " + id));
        Shop currentUserShop = getCurrentUserShop();
        if (!product.getShop().getId().equals(currentUserShop.getId())) {
            throw new UnauthorizedProductAccessException
                    ( "You cannot delete another shop's product");
        }
        productRepo.delete(product);
    }

    @Override
    public List<ProductResponse> searchProductsByCategory(Category category) {

        return productRepo.searchByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {

        return productRepo.searchProducts(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    private Shop getCurrentUserShop() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return shopRepository.findByOwner(user)
                .orElseThrow(() ->
                        new RuntimeException("Shop not found"));
    }

    @Override
    public List<ProductResponse> getMyShopProducts() {

        Shop shop = getCurrentUserShop();

        return productRepo.findByShop(shop)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}