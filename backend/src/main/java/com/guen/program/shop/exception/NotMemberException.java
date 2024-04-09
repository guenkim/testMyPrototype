package com.guen.program.shop.exception;

public class NotMemberException extends RuntimeException{
    private String message;

    public NotMemberException(String message) {
        this.message = message;
    }
}
