package com.example.demo.controller.service;

import com.example.demo.controller.pojo.MemberAccountDto;
import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final MemberAccountRepository memberAccountRepository;
    private final BCryptPasswordEncoder encode;
    private final MessageSource messageSource;

    public ResponseDto<String> start(MemberAccountDto memberAccountDto) {
        log.info("Accessing start() method - username: {} - {}", memberAccountDto.getUsername(), "Register attempt");

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
