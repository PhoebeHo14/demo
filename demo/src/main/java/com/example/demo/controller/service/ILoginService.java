package com.example.demo.controller.service;

import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;

public interface ILoginService {

     ResponseDto<String> start(MemberAccountDto memberAccountDo) throws Exception;
}
