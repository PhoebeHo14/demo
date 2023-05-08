package com.example.demo.controller.service;

import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.dao.repository.pojo.CheckInDo;
import com.example.demo.controller.pojo.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final MessageSource messageSource;

    public ResponseDto<String> start(String userId) {
        Integer id = Integer.valueOf(userId);

        if (isAlreadyCheckIn(id, LocalDate.now())) {
            String message = messageSource.getMessage("already.check.in", null, LocaleContextHolder.getLocale());
            throw new ServiceException(message);
        }

        CheckInDo checkInDo = new CheckInDo();
        checkInDo.setAccountId(id);
        checkInDo.setCheckInTime(LocalDateTime.now());
        checkInDo.setCheckInDate(LocalDate.now());

        checkInRepository.save(checkInDo);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String message = messageSource.getMessage("check.in.success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(message);

        return responseDto;
    }

    private boolean isAlreadyCheckIn(Integer id, LocalDate date) {
        return checkInRepository.findByAccountIdAndCheckInDate(id, date) != null;
    }
}
