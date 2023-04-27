package com.example.demo.controller;

import com.example.demo.exception.MemberAccountNotFoundException;
import com.example.demo.exception.PasswordNotMatchException;
import com.example.demo.exception.UsernameDuplicateException;
import com.example.demo.model.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UsernameDuplicateException.class)
    public ResponseDto<String> handleUsernameDuplicateException(UsernameDuplicateException usernameDuplicateException) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        responseDto.setStatus(-1);
        responseDto.setHttpStatusCode(HttpStatus.CONFLICT);
        return responseDto;
    }

    @ExceptionHandler(MemberAccountNotFoundException.class)
    public ResponseDto<String> handleMemberAccountNotFoundException(MemberAccountNotFoundException memberAccountNotFoundException) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        responseDto.setStatus(-1);
        responseDto.setHttpStatusCode(HttpStatus.NOT_FOUND);
        return responseDto;
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseDto<String> handlePasswordNotMatchException(PasswordNotMatchException passwordNotMatchException) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        responseDto.setStatus(-1);
        responseDto.setHttpStatusCode(HttpStatus.UNAUTHORIZED);
        return responseDto;
    }
}
