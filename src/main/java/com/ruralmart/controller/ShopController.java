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

    @GetMapping("/my-shop")
    public Shop getMyShop() {
        return shopService.getMyShop();
    }

    @PutMapping("/my-shop")
    public Shop updateMyShop(@Valid @RequestBody ShopRequest request) {
        return shopService.updateMyShop(request);
    }

}