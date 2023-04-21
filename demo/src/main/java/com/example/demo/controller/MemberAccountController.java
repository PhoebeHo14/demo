package com.example.demo.controller;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.IMemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class MemberAccountController {

    @Autowired
    private IMemberAccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody MemberAccount newMemberAccount){
        Integer id = accountService.register(newMemberAccount);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberAccount memberAccount){
        if(accountService.login(memberAccount) != null){
            return accountService.login(memberAccount);
        }else{
            return "login failed";
        }
    }
}
