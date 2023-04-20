package com.example.demo.service.impl;

import com.example.demo.dao.MemberAccountRepository;
import com.example.demo.entity.MemberAccount;
import com.example.demo.mapper.MemberAccountMapper;
import com.example.demo.service.IMemberAccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAccountService implements IMemberAccountService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;
    private MemberAccountMapper memberAccountMapper;

    @Override
    public Integer register(MemberAccount memberAccount) {

        MemberAccount newMember = new MemberAccount();
        String encodedPassword = BCrypt.hashpw(memberAccount.getPassword(), BCrypt.gensalt());
        newMember.setUsername(memberAccount.getUsername());
        newMember.setPassword(encodedPassword);

        memberAccountRepository.save(newMember);
        return newMember.getId();
    }

    @Override
    public Integer login(MemberAccount memberAccount) {

        MemberAccount m = memberAccountMapper.findMemberAccountByUsername(memberAccount.getUsername());

        //check if account exist
        if (m == null) {
            return null;
        }

        //check if password is correct
        if (BCrypt.checkpw(memberAccount.getPassword(), m.getPassword())) {
            return memberAccount.getId();
        } else {
            return null;
        }

    }

}
