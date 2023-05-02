package com.example.demo.controller.service;

import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.ResponseDto;

public interface IUpdatePasswordService {

    ResponseDto<String> start(MemberAccountDo memberAccountDo);
}
