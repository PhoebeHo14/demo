package com.example.demo.controller.service;

import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.dao.repository.pojo.CheckInDo;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import com.example.demo.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInFailService {

    private final CheckInRepository checkInRepository;
    private final MemberAccountRepository memberAccountRepository;

    public ResponseDto<String> start(String userId) {

        CheckInDo checkInDo = new CheckInDo();
        checkInDo.setAccountId(Integer.valueOf(userId));
        checkInDo.setCheckInTime(LocalDateTime.now());
        checkInDo.setType(1);
        checkInDo.setCheckInDate(LocalDate.now());

        checkInRepository.save(checkInDo);

//        memberAccountRepository.findById(Integer.parseInt(userId));
        log.error("Access Log - {} - {}", "userId:" + userId, "Check in failed");

        throw new ServiceException("Check in failed");
    }

}
