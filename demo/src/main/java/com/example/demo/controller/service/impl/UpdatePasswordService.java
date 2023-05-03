package com.example.demo.controller.service.impl;

import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    public ResponseDto<String> start(String userId, MemberAccountDto memberAccountDto) {

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(Integer.valueOf(userId));
        updateMemberDto.setPassword(getEncodedPassword(memberAccountDto));
        int rowsUpdated = memberAccountMapper.updatePassword(updateMemberDto);

        ResponseDto<String> responseDto = new ResponseDto<>();
        if (rowsUpdated > 0) {
            responseDto.setStatus(1);
            responseDto.setMessage("Password updated successfully");
        } else {
            responseDto.setStatus(-1);
            responseDto.setMessage("Failed to update password");
        }
        return responseDto;
    }

    private static String getEncodedPassword(MemberAccountDto memberAccountDto) {
        return BCrypt.hashpw(memberAccountDto.getPassword(), BCrypt.gensalt());
    }

}
