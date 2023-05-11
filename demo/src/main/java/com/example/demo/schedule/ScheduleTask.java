package com.example.demo.schedule;

import com.example.demo.controller.pojo.WorkTimeDo;
import com.example.demo.dao.repository.CheckInRepository;
import com.example.demo.dao.repository.WorkTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTask {

    private final CheckInRepository checkInRepository;
    private final WorkTimeRepository workTimeRepository;
    private final ThreadPoolTaskExecutor executor;

    @Scheduled(cron = "0/20 * * * * ? ")
    public void calculateWorkTime() {
        List<Integer> accountIds = checkInRepository.findDistinctAccountIds(LocalDate.now());

        for (Integer accountId : accountIds) {
            CompletableFuture.runAsync(() -> {
                LocalDateTime earliestCheckIn = checkInRepository.findEarliestCheckIn(accountId, LocalDate.now());
                LocalDateTime latestCheckOut = checkInRepository.findLatestCheckOut(accountId, LocalDate.now());

                System.out.println("accountID: " + accountId + ",earliestCheckIn: " + earliestCheckIn + ", latestCheckOut: " + latestCheckOut + "  " + LocalDateTime.now());

                if (earliestCheckIn != null && latestCheckOut != null) {
                    long workMinutes = ChronoUnit.MINUTES.between(earliestCheckIn, latestCheckOut);

                    WorkTimeDo workTimeDo = new WorkTimeDo();
                    workTimeDo.setAccountId(accountId);
                    workTimeDo.setWorkTime(workMinutes);
                    workTimeDo.setCheckInDate(LocalDate.now());

                    log.info("This is Slf4j INFO !!!");
                    log.debug("This is Slf4j DEBUG !!!");
                    log.error("This is Slf4j ERROR !!!");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("accountID: " + accountId + " calculate complete!!!"  + "  " + LocalDateTime.now());

                    workTimeRepository.save(workTimeDo);
                }
            }, executor);
        }
        log.info("Work hours calculation completed");
    }
}