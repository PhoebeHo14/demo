package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IUpdatePasswordService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService implements IUpdatePasswordService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public int updatePassword(MemberAccountDo memberAccountDo) {

        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String encodedPassword = BCrypt.hashpw(memberAccountDo.getPassword(), BCrypt.gensalt());

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(id);
        updateMemberDto.setPassword(encodedPassword);

        return memberAccountMapper.updatePassword(updateMemberDto);
    }

}
