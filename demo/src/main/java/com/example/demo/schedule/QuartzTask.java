package com.example.demo.schedule;

import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.WorkTimeRepository;
import com.example.demo.dao.repository.pojo.CheckInDo;
import com.example.demo.controller.pojo.WorkTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@Component
@EnableScheduling
public class QuartzTask {
    @Autowired
    CheckInRepository checkInRepository;
    @Autowired
    WorkTimeRepository workTimeRepository;

    public void calculateWorkTime() {
        List<CheckInDo> checkInDoList = checkInRepository.findByCheckInDate(LocalDate.now());

        for (CheckInDo checkInDo : checkInDoList) {
            LocalDateTime checkOutTime = checkInDo.getCheckOutTime();

            if (checkOutTime == null) {
                continue;
            }

            LocalDateTime checkInTime = checkInDo.getCheckInTime();

            Long workMinutes = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);  //todo need decimal point
            float workTime = ((float)workMinutes)/60;

            WorkTimeDto workTimeDto = new WorkTimeDto();
            workTimeDto.setAccountId(checkInDo.getAccountId());
            workTimeDto.setWorkTime(workTime);

            System.out.println("work time not calculated");

            workTimeRepository.save(workTimeDto);

            System.out.println("work time calculated");
        }
    }
}
