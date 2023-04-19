package com.example.demo.service;

import com.example.demo.entity.MemberAccount;

public interface IMemberAccountService {

    public void register(MemberAccount memberAccount);
    public Integer login(MemberAccount memberAccount);
}
