package com.example.demo.controller;

import com.example.demo.controller.service.CheckInFailService;
import com.example.demo.controller.service.CheckInService;
import com.example.demo.controller.service.CheckOutService;
import com.example.demo.controller.pojo.ResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Check In")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;
    private final CheckOutService checkOutService;
    private final CheckInFailService checkInFailService;

    @PostMapping("/check-in")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> checkIn(@AuthenticationPrincipal String userDetails) {
        return checkInService.start(userDetails);
    }

    @PostMapping("/check-out")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> checkOut(@AuthenticationPrincipal String userDetails) {
        return checkOutService.start(userDetails);
    }

    @PostMapping("/check-in-fail")
    @SecurityRequirement(name = "token")
    public ResponseDto<String> checkInFail(@AuthenticationPrincipal String userDetails) {
        return checkInFailService.start(userDetails);
    }
}
