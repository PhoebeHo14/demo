package com.example.demo.service;

import com.example.demo.entity.MemberAccount;

public interface IMemberAccountService {

    public Integer register(MemberAccount memberAccount);
    public String login(MemberAccount memberAccount);
}
