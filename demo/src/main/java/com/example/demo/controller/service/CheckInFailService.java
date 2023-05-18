package com.example.demo.controller.service;

import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.dao.repository.pojo.CheckInDo;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInFailService {

    private final CheckInRepository checkInRepository;
    private final MemberAccountRepository memberAccountRepository;

    public ResponseDto<String> start(String id) {
        Integer userId = Integer.valueOf(id);
        MemberAccountDo memberAccountDo = memberAccountRepository.getReferenceById(userId);
        String username = memberAccountDo.getUsername();

        log.debug("Starting check-in for user with username: {}", username);

        CheckInDo checkInDo = new CheckInDo();
        checkInDo.setAccountId(userId);
        checkInDo.setCheckInTime(LocalDateTime.now());

        checkInRepository.save(checkInDo);

        log.error("username: {} - {}", username, "Error occurred during check-in");

        throw new ServiceException("Check in failed");
    }

}
