package com.ruralmart.service;

import com.ruralmart.dto.ShopRequest;
import com.ruralmart.entity.Shop;

public interface ShopService {

    Shop createShop(ShopRequest request);

    Shop getMyShop();

    Shop updateMyShop(ShopRequest request);
}