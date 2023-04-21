package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IMemberAccountService;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.model.MemberAccount;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberAccountService implements IMemberAccountService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;
    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public Integer register(MemberAccount memberAccount) {

        MemberAccount newMember = new MemberAccount();
        String encodedPassword = BCrypt.hashpw(memberAccount.getPassword(), BCrypt.gensalt());
        newMember.setUsername(memberAccount.getUsername());
        newMember.setPassword(encodedPassword);

        System.out.println("Username :" + memberAccount.getUsername() + "Password :" + encodedPassword);
        memberAccountRepository.save(newMember);
        return newMember.getId();
    }

    @Override
    public String login(MemberAccount memberAccount) {

        String result = "000";

        MemberAccountDo memberAccountDo = memberAccountMapper.findByUsername(memberAccount.getUsername());

        //check if account exist
        if (memberAccountDo == null) {
            return null;
        }

        //check if password is correct
        if (BCrypt.checkpw(memberAccount.getPassword(), memberAccountDo.getPassword())) {

            Date expireDate =
                    //set expireTime as 30 mins
                    new Date(System.currentTimeMillis() + 30 * 60 * 1000);
            String jwtToken = Jwts.builder()
                    .setSubject(String.valueOf(memberAccountDo.getId()))
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
