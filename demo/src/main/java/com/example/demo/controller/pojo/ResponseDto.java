package com.example.demo.controller.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
    int status;
    T token;
    String message;

}