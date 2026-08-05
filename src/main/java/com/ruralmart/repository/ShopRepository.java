package com.ruralmart.repository;

import com.ruralmart.entity.Shop;
import com.ruralmart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByOwner(User owner);

    boolean existsByOwner(User owner);
}