package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IRegisterService;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.model.MemberAccountDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class registerService implements IRegisterService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public String register(MemberAccountDto memberAccountDto) {

        String username = memberAccountDto.getUsername();
        String encodedPassword = BCrypt.hashpw(memberAccountDto.getPassword(), BCrypt.gensalt());

        if (memberAccountRepository.findByUsername(username).isPresent()) {
            return "Username already exists: " + username;
        }

        MemberAccountDto newMember = new MemberAccountDto();
        newMember.setUsername(username);
        newMember.setPassword(encodedPassword);

        memberAccountRepository.save(newMember);
        return "Register Success";
    }

}
