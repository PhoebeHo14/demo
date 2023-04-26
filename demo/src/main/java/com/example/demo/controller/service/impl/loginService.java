package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.ILoginService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.util.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginService implements ILoginService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public String login(MemberAccountDto memberAccountDto) {
        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
            return null;
        }

        String password = memberAccountDto.getPassword();
        String hashedPassword = account.getPassword();
        boolean isPasswordCorrect = BCrypt.checkpw(password, hashedPassword);
        if (isPasswordCorrect) {
            String jwtToken = JwtUtils.generateToken(account.getId());
            return jwtToken;
        } else {
            return null;
        }
    }

}
