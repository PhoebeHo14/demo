package com.example.demo.controller.service;

import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.controller.pojo.UpdatePasswordDto;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePasswordService {

    private final MemberAccountMapper memberAccountMapper;
    private final BCryptPasswordEncoder encode;
    private final MessageSource messageSource;

    public ResponseDto<String> start(String id, UpdatePasswordDto updatePasswordDto) {
        Integer userId = Integer.valueOf(id);
        MemberAccountDo memberAccountDo = memberAccountMapper.findById(userId);
        log.info("Accessing start() method - username: {} - {}", memberAccountDo.getUsername(), "Update password attempt");

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(userId);
        updateMemberDto.setPassword(encode.encode(updatePasswordDto.getPassword()));
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
