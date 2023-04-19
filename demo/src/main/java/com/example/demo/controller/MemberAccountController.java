package com.example.demo.controller;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.MemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberAccountController {

    @Autowired
    private MemberAccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(MemberAccount memberAccount){

        String registerResult = accountService.register(memberAccount);
        return registerResult;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public  String login(){

        return "Login Success";
    }
}
