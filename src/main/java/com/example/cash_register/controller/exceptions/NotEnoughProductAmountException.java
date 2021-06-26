package com.example.cash_register.controller.exceptions;

public class NotEnoughProductAmountException extends RuntimeException {
    private final String message;

    public NotEnoughProductAmountException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
