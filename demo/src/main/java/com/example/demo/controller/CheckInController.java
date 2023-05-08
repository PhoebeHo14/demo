package com.example.demo.controller;

import com.example.demo.controller.service.CheckInService;
import com.example.demo.controller.service.CheckOutService;
import com.example.demo.controller.pojo.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Check In")
public class CheckInController {
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckOutService checkOutService;

    @PostMapping("/check-in")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> checkIn(@Parameter(hidden = true) @AuthenticationPrincipal String userDetails) {
        return checkInService.start(userDetails);
    }

    @PostMapping("/check-out")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> checkOut(@Parameter(hidden = true) @AuthenticationPrincipal String userDetails) {
        return checkOutService.start(userDetails);
    }
}
