package com.guen.program.shop.exception;

public class OrderNotFindException extends RuntimeException{
    private String message;

    public OrderNotFindException(String message) {
        this.message = message;
    }
}
