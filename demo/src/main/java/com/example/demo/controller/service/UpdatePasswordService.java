package com.example.demo.controller.service;

import com.example.demo.controller.pojo.UpdatePasswordDto;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.controller.pojo.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService {

    @Autowired
    MemberAccountMapper memberAccountMapper;
    @Autowired
    BCryptPasswordEncoder encode;
    @Autowired
    MessageSource messageSource;

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
