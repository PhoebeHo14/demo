package com.example.demo.controller;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.IMemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberAccountController {

    @Autowired
    private IMemberAccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Integer register(MemberAccount newMemberAccount){

        String registerResult = accountService.register(newMemberAccount);
        return newMemberAccount.getId();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public  String login(MemberAccount memberAccount){


        return "Login Success";
    }
}
