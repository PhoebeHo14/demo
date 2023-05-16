package com.example.demo.exception;

public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -4513514459698548384L;

    public ServiceException(String message) {
        super(message);
    }
}
