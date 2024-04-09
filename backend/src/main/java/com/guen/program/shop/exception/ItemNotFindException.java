package com.guen.program.shop.exception;

public class ItemNotFindException extends RuntimeException{
    private String message;

    public ItemNotFindException(String message) {
        this.message = message;
    }
}
