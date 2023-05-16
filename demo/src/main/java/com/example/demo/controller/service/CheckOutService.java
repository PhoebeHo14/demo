package com.example.demo.controller.service;

import com.example.demo.controller.pojo.ResponseDto;
import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.MemberAccountRepository;
import com.example.demo.dao.repository.pojo.CheckInDo;
import com.example.demo.dao.repository.pojo.MemberAccountDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckOutService {

    private final CheckInRepository checkInRepository;
    private final MemberAccountRepository memberAccountRepository;
    private final MessageSource messageSource;

    public ResponseDto<String> start(String id) {
        Integer userId = Integer.valueOf(id);
        LocalDateTime checkOutTime = LocalDateTime.now();
        LocalDate checkOutDate = LocalDate.now();
        MemberAccountDo memberAccountDo = memberAccountRepository.getReferenceById(userId);
        log.info("Accessing start() method - username: {} - checkOutTime: {} - {}", memberAccountDo.getUsername(), checkOutTime, "Check out attempt");

        CheckInDo checkInDo = new CheckInDo();
        checkInDo.setAccountId(userId);
        checkInDo.setCheckInTime(checkOutTime);
        checkInDo.setType(0);
        checkInDo.setCheckInDate(checkOutDate);
        checkInRepository.save(checkInDo);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String message = messageSource.getMessage("check.out.success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(message);

        return responseDto;
    }
}
