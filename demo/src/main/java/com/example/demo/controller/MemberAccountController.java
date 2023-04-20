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

    @PostMapping
    public ResponseEntity<Integer> register(@RequestBody MemberAccount newMemberAccount){
        Integer id = accountService.register(newMemberAccount);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody MemberAccount memberAccount){
        Integer id = accountService.login(memberAccount);
        if(id != null){
            return new ResponseEntity<>(id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
