package com.guen.program.todo.exception;

public class TodoNotFindException extends RuntimeException{
    private String message;

    public TodoNotFindException(String message) {
        this.message = message;
    }
}
