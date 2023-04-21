package com.example.demo.controller.service;

import com.example.demo.model.MemberAccount;

public interface IMemberAccountService {

    public Integer register(MemberAccount memberAccount);
    public String login(MemberAccount memberAccount);
}
