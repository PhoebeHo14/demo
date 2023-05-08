package com.example.demo.dao.repository;

import com.example.demo.dao.repository.pojo.CheckInDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInDo, Integer> {
    CheckInDo save(CheckInDo checkInDo);
    List<CheckInDo> findByCheckInDate(LocalDate date);
    CheckInDo findByAccountId(Integer id);
    CheckInDo findByAccountIdAndCheckInDate(Integer accountId, LocalDate checkInDate);
}
