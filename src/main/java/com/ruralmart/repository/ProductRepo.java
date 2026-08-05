package com.ruralmart.repository;

import com.ruralmart.entity.Shop;
import com.ruralmart.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruralmart.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("""
       SELECT p
       FROM Product p
       WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       """)
    List<Product> searchProducts(@Param("keyword") String keyword);

    List<Product> searchByCategory(Category category);

    List<Product> findByShop(Shop shop);

}
