package com.example.demo.controller;

import com.example.demo.model.MemberAccountDto;
import com.example.demo.controller.service.IMemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class MemberAccountController {

    @Autowired
    private IMemberAccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody MemberAccountDto newMemberAccountDto){
        Integer id = accountService.register(newMemberAccountDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberAccountDto memberAccountDto){
        String jwtToken = accountService.login(memberAccountDto);
        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
