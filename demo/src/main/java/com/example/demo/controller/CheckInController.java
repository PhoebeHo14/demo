package com.example.demo.controller;

import com.example.demo.controller.service.CheckInService;
import com.example.demo.controller.pojo.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/check-in")
    @Operation(summary = "上班打卡", description = "需先登入")
    public ResponseDto<String> checkIn(@AuthenticationPrincipal String userDetails) {
        return checkInService.start(userDetails);
    }
}
