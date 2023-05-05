package com.example.demo.controller;

import com.example.demo.controller.service.LoginService;
import com.example.demo.controller.service.RegisterService;
import com.example.demo.controller.service.UpdatePasswordService;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class MemberAccountController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UpdatePasswordService updatePasswordService;

    @PostMapping("/register")
    public ResponseDto<String> register(@RequestBody MemberAccountDto newMemberAccountDto){
        return registerService.start(newMemberAccountDto);
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody MemberAccountDto memberAccountDto) {
        return loginService.start(memberAccountDto);
    }

    @PostMapping("/update-password")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> updatePassword(@Parameter(hidden = true)@AuthenticationPrincipal String userDetails, @RequestBody MemberAccountDto memberAccountDto) {
        return updatePasswordService.start(userDetails, memberAccountDto);
    }
}