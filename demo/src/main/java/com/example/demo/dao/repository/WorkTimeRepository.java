package com.example.demo.dao.repository;

import com.example.demo.controller.pojo.WorkTimeDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTimeDo, Integer> {

    WorkTimeDo findByAccountIdAndCheckInDate(Integer accountId, LocalDate checkInDate);
}
