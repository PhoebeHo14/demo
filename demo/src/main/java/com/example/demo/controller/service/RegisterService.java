package com.example.demo.controller.service;

import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RegisterService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;
    @Autowired
    private BCryptPasswordEncoder encode;
    @Autowired
    MessageSource messageSource;
    private Locale locale;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        if (isAccountExist(memberAccountDto)) {
            String message = messageSource.getMessage("account.duplicate", null, locale);
            throw new ServiceException(message);
        }

        return saveAccount(memberAccountDto);
    }

    private boolean isAccountExist(MemberAccountDto memberAccountDto) {
        return memberAccountRepository.findByUsername(memberAccountDto.getUsername()) != null;
    }

    private ResponseDto<String> saveAccount(MemberAccountDto memberAccountDto) {
        String encodedPassword = encode.encode(memberAccountDto.getPassword());

        MemberAccountDo newMemberDo = new MemberAccountDo();
        newMemberDo.setUsername(memberAccountDto.getUsername());
        newMemberDo.setPassword(encodedPassword);

        memberAccountRepository.save(newMemberDo);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String message = messageSource.getMessage("login.success", null, locale);
        responseDto.setMessage(message);

        return responseDto;
    }
}
