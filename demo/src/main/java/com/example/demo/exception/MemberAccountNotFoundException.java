package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class MemberAccountNotFoundException extends RuntimeException{
    public MemberAccountNotFoundException(String message) {
        super(message);
    }
}
