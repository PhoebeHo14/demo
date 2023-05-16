package com.example.demo.controller;

import com.example.demo.controller.pojo.MemberAccountDto;
import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.controller.pojo.UpdatePasswordDto;
import com.example.demo.controller.service.LoginService;
import com.example.demo.controller.service.RegisterService;
import com.example.demo.controller.service.UpdatePasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Member Account")
@RequiredArgsConstructor
public class MemberAccountController {

    private final RegisterService registerService;
    private final LoginService loginService;
    private final UpdatePasswordService updatePasswordService;

    @PostMapping("/register")
    @Operation(summary = "註冊")
    public ResponseDto<String> register(@RequestBody MemberAccountDto newMemberAccountDto) {
        return registerService.start(newMemberAccountDto);
    }

    @PostMapping("/login")
    @Operation(summary = "登入")
    public ResponseDto<String> login(@RequestBody MemberAccountDto memberAccountDto) {
        return loginService.start(memberAccountDto);
    }

    @PostMapping("/update-password")
    @Operation(summary = "修改密碼", description = "修改密碼請先登入")
    public ResponseDto<String> updatePassword(@AuthenticationPrincipal String userDetails, @RequestBody UpdatePasswordDto updatePasswordDto) {
        return updatePasswordService.start(userDetails, updatePasswordDto);
    }
}