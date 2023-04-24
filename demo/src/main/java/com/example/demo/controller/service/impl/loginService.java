package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.ILoginService;
import com.example.demo.controller.service.IRegisterService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.model.MemberAccountDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class loginService implements ILoginService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;
    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public String login(MemberAccountDto memberAccount) {

        String result = "000";

        MemberAccountDto memberAccountDto = memberAccountMapper.findByUsername(memberAccount.getUsername());

        //check if account exist
        if (memberAccountDto == null) {
            return null;
        }

        //check if password is correct
        if (BCrypt.checkpw(memberAccount.getPassword(), memberAccountDto.getPassword())) {

            Date expireDate =
                    //set expireTime as 30 mins
                    new Date(System.currentTimeMillis() + 30 * 60 * 1000);
            String jwtToken = Jwts.builder()
                    .setSubject(String.valueOf(memberAccountDto.getId()))
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, "MySecret")
                    .compact();
            result = jwtToken;
            return result;
        } else {
            return null;
        }

    }

}
