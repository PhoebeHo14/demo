package com.example.demo.controller.service;

import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;

public interface IRegisterService {

    ResponseDto<String> start(MemberAccountDto memberAccountDo);
}
