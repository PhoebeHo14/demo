package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IUpdatePasswordService;
import com.example.demo.dao.mybatis.MemberAccountMapper;
import com.example.demo.model.MemberAccountDo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class updatePasswordService implements IUpdatePasswordService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    @Override
    public int updatePassword(MemberAccountDo memberAccountDo) {

        Integer id = memberAccountDo.getId();
        String encodedPassword = BCrypt.hashpw(memberAccountDo.getPassword(), BCrypt.gensalt());

        MemberAccountDo updateMemberDto = new MemberAccountDo();
        updateMemberDto.setId(id);
        updateMemberDto.setPassword(encodedPassword);

        return memberAccountMapper.updatePassword(updateMemberDto);
    }

}
