package com.example.demo.controller.service.impl;

import com.example.demo.controller.service.IRegisterService;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.exception.UsernameDuplicateException;
import com.example.demo.model.MemberAccountDo;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public ResponseDto start(MemberAccountDto memberAccountDto) {

        if (isAccountExist(memberAccountDto)) {
            throw new UsernameDuplicateException("Username already exists");
        }

        return saveAccount(memberAccountDto);
    }

    private boolean isAccountExist(MemberAccountDto memberAccountDto) {
        return memberAccountRepository.findByUsername(memberAccountDto.getUsername()) != null;
    }

    private ResponseDto<String> saveAccount(MemberAccountDto memberAccountDto) {
        String encodedPassword = BCrypt.hashpw(memberAccountDto.getPassword(), BCrypt.gensalt());

        MemberAccountDo newMemberDo = new MemberAccountDo();
        newMemberDo.setUsername(memberAccountDto.getUsername());
        newMemberDo.setPassword(encodedPassword);

        memberAccountRepository.save(newMemberDo);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);

        return responseDto;
    }
}
