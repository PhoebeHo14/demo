package com.example.demo.service.impl;

import com.example.demo.dao.MemberAccountRepository;
import com.example.demo.entity.MemberAccount;
import com.example.demo.service.IMemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAccountService implements IMemberAccountService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public String register(MemberAccount memberAccount) {

        MemberAccount newMember = new MemberAccount();
        newMember.setAccount(memberAccount.getAccount());
        newMember.setPassword(memberAccount.getPassword());

        memberAccountRepository.save(newMember);
        return null;
    }

}
