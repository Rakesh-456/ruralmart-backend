package com.ruralmart.exception;

public class UnauthorizedProductAccessException extends RuntimeException {

    public UnauthorizedProductAccessException(String message) {
        super(message);
    }
}