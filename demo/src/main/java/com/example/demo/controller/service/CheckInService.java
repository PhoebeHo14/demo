package com.example.demo.controller.service;

import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.CheckInDto;
import com.example.demo.model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class CheckInService {
    @Autowired
    CheckInRepository checkInRepository;
    @Autowired
    MessageSource messageSource;
    private Locale locale;

    public ResponseDto<String> start(String userId) {
        Integer id = Integer.valueOf(userId);

        if (isAlreadyCheckIn(id, LocalDate.now())) {
            String message = messageSource.getMessage("already.check.in", null, locale);
            throw new ServiceException(message);
        }

        CheckInDto checkInDto = new CheckInDto();
        checkInDto.setAccountId(id);
        checkInDto.setCheckInTime(LocalDateTime.now());
        checkInDto.setCheckInDate(LocalDate.now());

        checkInRepository.save(checkInDto);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String message = messageSource.getMessage("check.in.success", null, locale);
        responseDto.setMessage(message);

        return responseDto;
    }

    private boolean isAlreadyCheckIn(Integer id, LocalDate date) {
        return checkInRepository.findByAccountIdAndCheckInDate(id, date) != null;
    }
}
