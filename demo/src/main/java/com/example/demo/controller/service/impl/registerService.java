package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IRegisterService;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class registerService implements IRegisterService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;
    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public Integer register(MemberAccountDto memberAccountDto) {

        MemberAccountDto newMember = new MemberAccountDto();
        String encodedPassword = BCrypt.hashpw(memberAccountDto.getPassword(), BCrypt.gensalt());
        newMember.setUsername(memberAccountDto.getUsername());
        newMember.setPassword(encodedPassword);

        System.out.println("Username :" + memberAccountDto.getUsername() + "Password :" + encodedPassword);
        memberAccountRepository.save(newMember);
        return newMember.getId();
    }

}
