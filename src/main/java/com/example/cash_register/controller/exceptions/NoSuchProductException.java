package com.example.cash_register.controller.exceptions;

public class NoSuchProductException extends RuntimeException {
    private final String message;

    public NoSuchProductException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
