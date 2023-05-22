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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTask {

    private final CheckInRepository checkInRepository;
    private final WorkTimeRepository workTimeRepository;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Scheduled(cron = "0/20 * * * * ? ")
    public void calculateWorkTime() {
        List<Integer> accountIds = checkInRepository.findDistinctAccountIds(LocalDate.now());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Integer accountId : accountIds) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                LocalDateTime earliestCheckIn = checkInRepository.findEarliestCheckIn(accountId, LocalDate.now());
                LocalDateTime latestCheckIn = checkInRepository.findLatestCheckIn(accountId, LocalDate.now());

                log.info("accountId: {} - Work time calculating...... ", accountId);

                if (earliestCheckIn != null && latestCheckIn != null) {
                    long workMinutes = ChronoUnit.MINUTES.between(earliestCheckIn, latestCheckIn);

                    WorkTimeDo existingRecord = workTimeRepository.findByAccountIdAndCheckInDate(accountId, LocalDate.now());
                    if (existingRecord != null) {
                        existingRecord.setWorkMinute(workMinutes);
                        workTimeRepository.save(existingRecord);
                    } else {
                        WorkTimeDo workTimeDo = new WorkTimeDo();
                        workTimeDo.setAccountId(accountId);
                        workTimeDo.setWorkMinute(workMinutes);
                        workTimeDo.setCheckInDate(LocalDate.now());
                        workTimeRepository.save(workTimeDo);
                    }
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("accountId: {} - Calculate complete!!! ", accountId);

            }, taskExecutor);

            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("Work hours calculation completed");
    }
}