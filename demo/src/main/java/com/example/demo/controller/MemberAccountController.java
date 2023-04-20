package com.example.demo.controller;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.IMemberAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api
public class MemberAccountController {

    @Autowired
    private IMemberAccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation("register")
    public Integer register(@ModelAttribute MemberAccount newMemberAccount){

        accountService.register(newMemberAccount);
        return newMemberAccount.getId();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("login")
    public Integer login(@ModelAttribute MemberAccount memberAccount){

        return accountService.login(memberAccount);
    }
}
