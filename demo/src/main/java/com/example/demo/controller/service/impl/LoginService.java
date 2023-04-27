package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.ILoginService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.exception.MemberAccountNotFoundException;
import com.example.demo.exception.PasswordNotMatchException;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import com.example.demo.util.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
           throw new MemberAccountNotFoundException("Member account not found");
        }
        return verifyPassword(memberAccountDto, account);
    }

    private ResponseDto<String> verifyPassword(MemberAccountDto memberAccountDto, MemberAccountDo accountDo) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(-1);

        String password = memberAccountDto.getPassword();
        String hashedPassword = accountDo.getPassword();
        boolean isPasswordCorrect = BCrypt.checkpw(password, hashedPassword);
        if (isPasswordCorrect) {
            String jwtToken = JwtUtils.generateToken(accountDo.getId());
            responseDto.setStatus(1);
            responseDto.setData(jwtToken);
            return responseDto;
        } else {
            throw new PasswordNotMatchException("Wrong password");
        }
    }
}
