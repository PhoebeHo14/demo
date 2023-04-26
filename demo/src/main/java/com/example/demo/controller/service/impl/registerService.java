package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IRegisterService;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class registerService implements IRegisterService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public String register(MemberAccountDto memberAccountDo) {

        String username = memberAccountDo.getUsername();
        String encodedPassword = BCrypt.hashpw(memberAccountDo.getPassword(), BCrypt.gensalt());

        if (memberAccountRepository.findByUsername(username).isPresent()) {
            return "Username already exists: " + username;
        }

        MemberAccountDo newMemberDto = new MemberAccountDo();
        newMemberDto.setUsername(username);
        newMemberDto.setPassword(encodedPassword);

        memberAccountRepository.save(newMemberDto);
        return "Register Success";
    }

}
