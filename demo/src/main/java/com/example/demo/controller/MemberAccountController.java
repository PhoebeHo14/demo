package com.example.demo.controller;

import com.example.demo.controller.service.ILoginService;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.controller.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class MemberAccountController {

    @Autowired
    private IRegisterService registerService;
    @Autowired
    private ILoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberAccountDto newMemberAccountDto){
        String message = registerService.register(newMemberAccountDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberAccountDto memberAccountDto){
        String jwtToken = loginService.login(memberAccountDto);
        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
