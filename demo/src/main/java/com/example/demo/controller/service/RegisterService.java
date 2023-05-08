package com.example.demo.controller.service;

import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.controller.pojo.MemberAccountDto;
import com.example.demo.controller.pojo.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    MemberAccountRepository memberAccountRepository;
    @Autowired
    BCryptPasswordEncoder encode;
    @Autowired
    MessageSource messageSource;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {

        if (isAccountExist(memberAccountDto)) {
            String message = messageSource.getMessage("account.duplicate", null, LocaleContextHolder.getLocale());
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
        String message = messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(message);

        return responseDto;
    }
}
