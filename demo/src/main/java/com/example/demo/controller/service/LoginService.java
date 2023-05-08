package com.example.demo.controller.service;

import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.controller.pojo.MemberAccountDto;
import com.example.demo.controller.pojo.ResponseDto;
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
    MemberAccountMapper memberAccountMapper;
    @Autowired
    BCryptPasswordEncoder encode;
    @Value("${jwt.secret}")
    String secret;
    @Autowired
    MessageSource messageSource;
    Locale locale;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
            String message = messageSource.getMessage("account.not.found", null, locale);
            throw new ServiceException(message);
        }

        if (encode.matches(memberAccountDto.getPassword(),account.getPassword())) {
            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            String message = messageSource.getMessage("login.success", null, locale);
            responseDto.setMessage(message);
            responseDto.setToken(JwtUtil.generateToken(account.getId(), secret));
            return responseDto;
        } else {
            String message = messageSource.getMessage("wrong.password", null, locale);
            throw new ServiceException(message);
        }
    }

}