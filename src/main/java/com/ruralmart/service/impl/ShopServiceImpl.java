package com.ruralmart.service.impl;

import com.ruralmart.dto.ShopRequest;
import com.ruralmart.entity.Shop;
import com.ruralmart.entity.User;
import com.ruralmart.exception.ShopAlreadyExistsException;
import com.ruralmart.repository.ShopRepository;
import com.ruralmart.repository.UserRepository;
import com.ruralmart.service.ShopService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopServiceImpl(ShopRepository shopRepository,
                           UserRepository userRepository) {

        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Shop createShop(ShopRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (shopRepository.existsByOwner(owner)) {
            throw new ShopAlreadyExistsException("You already own a shop");
        }

        Shop shop = new Shop();

        shop.setShopName(request.getShopName());
        shop.setPhoneNumber(request.getPhoneNumber());
        shop.setAddress(request.getAddress());
        shop.setDescription(request.getDescription());

        shop.setOwner(owner);

        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());

        return shopRepository.save(shop);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public Shop getMyShop() {

        User owner = getCurrentUser();

        return shopRepository.findByOwner(owner)
                .orElseThrow(() ->
                        new RuntimeException("Shop not found"));
    }

    @Override
    public Shop updateMyShop(ShopRequest request) {

        Shop shop = getMyShop();

        shop.setShopName(request.getShopName());

        shop.setPhoneNumber(request.getPhoneNumber());

        shop.setAddress(request.getAddress());

        shop.setDescription(request.getDescription());

        shop.setUpdatedAt(LocalDateTime.now());

        return shopRepository.save(shop);
    }
}