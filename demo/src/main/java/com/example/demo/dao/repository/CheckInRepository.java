package com.example.demo.dao.repository;

import com.example.demo.controller.pojo.CheckInDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInDto, Integer> {
    CheckInDto save(CheckInDto checkInDto);
    List<CheckInDto> findByCheckInDate(LocalDate date);
    CheckInDto findByAccountId(Integer id);
    CheckInDto findByAccountIdAndCheckInDate(Integer accountId, LocalDate checkInDate);
}
