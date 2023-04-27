package com.example.demo.exception;

public class UsernameDuplicateException extends RuntimeException{

    public UsernameDuplicateException(String message) {
        super(message);
    }
}
