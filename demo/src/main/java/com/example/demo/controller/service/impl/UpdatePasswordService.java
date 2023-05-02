package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IUpdatePasswordService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.ResponseDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService implements IUpdatePasswordService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public ResponseDto<String> start(MemberAccountDo memberAccountDo) {

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(getCurrentUserId());
        updateMemberDto.setPassword(getEncodedPassword(memberAccountDo));
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

    private static String getEncodedPassword(MemberAccountDo memberAccountDo) {
        return BCrypt.hashpw(memberAccountDo.getPassword(), BCrypt.gensalt());
    }

    private static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Integer) authentication.getPrincipal();
    }

}
