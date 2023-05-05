package com.example.demo.dao.repository;

import com.example.demo.model.CheckInDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInDto, Integer> {
    CheckInDto save(CheckInDto checkInDto);
    CheckInDto findByCheckInDate(LocalDate date);
    CheckInDto findByAccountId(Integer id);
    CheckInDto findByAccountIdAndCheckInDate(Integer accountId, LocalDate checkInDate);
}
