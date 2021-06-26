package com.example.cash_register.controller.exceptions;

public class ExistingProductNameException extends RuntimeException{

    private final String message;

    public ExistingProductNameException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
