package com.guen.program.todo.exception;

public class PathVariableException extends RuntimeException{

    private String message;

    public PathVariableException(String message) {

        this.message = message;
    }


}
