package com.example.demo.controller.service;

import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;

public interface IUpdatePasswordService {

    ResponseDto<String> start(String userId, MemberAccountDto memberAccountDto);
}
