package com.example.demo.controller.service;

import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.controller.pojo.UpdatePasswordDto;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordService {

    private final MemberAccountMapper memberAccountMapper;
    private final BCryptPasswordEncoder encode;
    private final MessageSource messageSource;

    public ResponseDto<String> start(String userId, UpdatePasswordDto memberAccountDto) {

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(Integer.valueOf(userId));
        updateMemberDto.setPassword(encode.encode(memberAccountDto.getPassword()));
        int rowsUpdated = memberAccountMapper.updatePassword(updateMemberDto);

        ResponseDto<String> responseDto = new ResponseDto<>();
        if (rowsUpdated > 0) {
            responseDto.setStatus(1);
            String message = messageSource.getMessage("password.updated", null, LocaleContextHolder.getLocale());
            responseDto.setMessage(message);
        } else {
            responseDto.setStatus(-1);
            String message = messageSource.getMessage("password.update.failed", null, LocaleContextHolder.getLocale());
            responseDto.setMessage(message);
        }
        return responseDto;
    }

}
