package com.example.demo.controller.service;

import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LoginService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;
    @Autowired
    private BCryptPasswordEncoder encode;
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    MessageSource messageSource;
    private Locale locale;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
            throw new ServiceException("Member account not found");
        }

        if (encode.matches(memberAccountDto.getPassword(),account.getPassword())) {
            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            String message = messageSource.getMessage("login.success", null, locale);
            responseDto.setMessage(message);
            responseDto.setToken(JwtUtil.generateToken(account.getId(), secret));
            return responseDto;
        } else {
            throw new ServiceException("Wrong password");
        }
    }

}