package com.ruralmart.exception;

public class ShopAlreadyExistsException extends RuntimeException {

    public ShopAlreadyExistsException(String message) {
        super(message);
    }
}