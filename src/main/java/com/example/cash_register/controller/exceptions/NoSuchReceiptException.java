package com.example.cash_register.controller.exceptions;

public class NoSuchReceiptException extends RuntimeException {

    private final String message;

    public NoSuchReceiptException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
