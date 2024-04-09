package com.guen.sign.Exception;

import lombok.Getter;

@Getter
public class NotValidIdOrPasswordException extends RuntimeException{
    private String message;

    public NotValidIdOrPasswordException(String message) {
        this.message = message;
    }
}
