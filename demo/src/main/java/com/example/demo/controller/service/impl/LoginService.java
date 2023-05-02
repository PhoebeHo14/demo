package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.ILoginService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import com.example.demo.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
            throw new ServiceException("Member account not found");
        }

        if (verifyPassword(memberAccountDto.getPassword(), account.getPassword())) {
            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            responseDto.setMessage("Login success");
            responseDto.setToken(JwtUtils.generateToken(account.getId()));
            return responseDto;
        } else {
            throw new ServiceException("Wrong password");
        }
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}