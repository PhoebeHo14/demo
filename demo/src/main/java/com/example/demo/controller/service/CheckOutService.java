package com.example.demo.controller.service;

import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.controller.pojo.CheckInDto;
import com.example.demo.controller.pojo.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class CheckOutService {
    @Autowired
    CheckInRepository checkInRepository;
    @Autowired
    MessageSource messageSource;
    Locale locale;
    public ResponseDto<String> start(String userId) {
        Integer id = Integer.valueOf(userId);
        CheckInDto checkInDto = checkInRepository.findByAccountIdAndCheckInDate(id, LocalDate.now());

        if(checkInDto == null){
            String message = messageSource.getMessage("not.check.in", null, locale);
            throw new ServiceException(message);
        }

        checkInDto.setCheckOutTime(LocalDateTime.now());

        checkInRepository.save(checkInDto);

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String message = messageSource.getMessage("check.out.success", null, locale);
        responseDto.setMessage(message);

        return responseDto;
    }
}
