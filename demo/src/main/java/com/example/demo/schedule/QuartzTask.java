package com.example.demo.schedule;

import com.example.demo.controller.pojo.WorkTimeDo;
import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.WorkTimeRepository;
import com.example.demo.dao.repository.pojo.CheckInDo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.*;

@Configuration
@Component
@EnableScheduling
@RequiredArgsConstructor
public class QuartzTask {

    private final CheckInRepository checkInRepository;
    private final WorkTimeRepository workTimeRepository;

    public void calculateWorkTime() {
        List<CheckInDo> checkInDtoList = checkInRepository.findByCheckInDate(LocalDate.now());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        for (CheckInDo checkInDo : checkInDtoList) {
            LocalDateTime checkOutTime = checkInDo.getCheckOutTime();

            if (checkOutTime == null) {
                continue;
            }

            LocalDateTime checkInTime = checkInDo.getCheckInTime();
            Integer accountId = checkInDo.getAccountId();

            CompletableFuture.runAsync(() -> {
                long workMinutes = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
                float workTime = ((float) workMinutes) / 60;

                WorkTimeDo workTimeDo = new WorkTimeDo();
                workTimeDo.setAccountId(accountId);
                workTimeDo.setWorkTime(workTime);

                workTimeRepository.save(workTimeDo);
            }, executor);
        }
    }
}