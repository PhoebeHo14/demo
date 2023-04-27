package com.example.demo.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
