package com.example.demo.controller;

import com.example.demo.controller.service.CheckInService;
import com.example.demo.model.MemberAccountDto;
import com.example.demo.model.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public class CheckInController {
    @Autowired
    CheckInService checkInService;

    public ResponseDto<String> updatePassword(@Parameter(hidden = true) @AuthenticationPrincipal String userDetails) {
        return checkInService.start(userDetails);
    }
}
