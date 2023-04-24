package com.example.demo.controller.service;

import com.example.demo.model.MemberAccountDto;

public interface IMemberAccountService {

    public Integer register(MemberAccountDto memberAccountDto);
    public String login(MemberAccountDto memberAccountDto);
}
