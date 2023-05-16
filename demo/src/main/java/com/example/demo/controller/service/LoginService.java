package com.example.demo.controller.service;

import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.controller.pojo.MemberAccountDto;
import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberAccountMapper memberAccountMapper;
    private final BCryptPasswordEncoder encode;
    @Value("${jwt.secret}")
    String secret;
    private final MessageSource messageSource;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {
        log.info("Accessing start() method - username: {} - {}", memberAccountDto.getUsername(), "Login attempt");

        MemberAccountDo account = memberAccountMapper.findByUsername(memberAccountDto.getUsername());
        if (account == null) {
            String message = messageSource.getMessage("account.not.found", null, LocaleContextHolder.getLocale());
            throw new ServiceException(message);
        }

        if (encode.matches(memberAccountDto.getPassword(),account.getPassword())) {
            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            String message = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
            responseDto.setMessage(message);
            responseDto.setToken(JwtUtil.generateToken(account.getId(), secret));
            return responseDto;
        } else {
            String message = messageSource.getMessage("wrong.password", null, LocaleContextHolder.getLocale());
            throw new ServiceException(message);
        }
    }

}