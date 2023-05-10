package com.example.demo.dao.repository;

import com.example.demo.dao.repository.pojo.CheckInDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInDo, Integer> {
    CheckInDo save(CheckInDo checkInDo);
    @Query("SELECT MIN(c.checkInTime) FROM check_in c WHERE c.accountId = :accountId AND c.type = 1 AND c.checkInDate = :localDate")
    LocalDateTime findEarliestCheckIn(@Param("accountId") Integer accountId, @Param("localDate") LocalDate localDate);

    @Query("SELECT MAX(c.checkInTime) FROM check_in c WHERE c.accountId = :accountId AND c.type = 0 AND c.checkInDate = :localDate")
    LocalDateTime findLatestCheckOut(@Param("accountId") Integer accountId, @Param("localDate") LocalDate localDate);

    @Query("SELECT DISTINCT c.accountId FROM check_in c WHERE c.checkInDate = :localDate")
    List<Integer> findDistinctAccountIds(@Param("localDate") LocalDate localDate);
}
