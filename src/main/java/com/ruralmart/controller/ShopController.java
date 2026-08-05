package com.ruralmart.controller;

import com.ruralmart.dto.ShopRequest;
import com.ruralmart.entity.Shop;
import com.ruralmart.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public Shop createShop(@Valid @RequestBody ShopRequest request) {
        return shopService.createShop(request);

    }

}