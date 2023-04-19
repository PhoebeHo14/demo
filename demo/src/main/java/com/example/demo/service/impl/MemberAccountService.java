package com.example.demo.service.impl;

import com.example.demo.dao.MemberAccountRepository;
import com.example.demo.entity.MemberAccount;
import com.example.demo.service.IMemberAccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAccountService implements IMemberAccountService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public String register(MemberAccount memberAccount) {

        MemberAccount newMember = new MemberAccount();
        String encodedPassword = BCrypt.hashpw(memberAccount.getPassword(), BCrypt.gensalt());
        newMember.setAccount(memberAccount.getAccount());
        newMember.setPassword(encodedPassword);

        memberAccountRepository.save(newMember);
        return null;
    }

}
