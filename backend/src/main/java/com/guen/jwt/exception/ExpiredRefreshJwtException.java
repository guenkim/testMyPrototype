package com.guen.jwt.exception;

import lombok.Getter;

@Getter
public class ExpiredRefreshJwtException  extends RuntimeException{

    private String message;
    public ExpiredRefreshJwtException(String message) {
        this.message = message;
    }


}
