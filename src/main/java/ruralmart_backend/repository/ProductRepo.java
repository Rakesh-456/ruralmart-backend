package ruralmart_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruralmart_backend.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
