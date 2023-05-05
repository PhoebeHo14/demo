package com.example.demo.dao.repository;

import com.example.demo.model.WorkTimeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTimeDto, Integer> {
    WorkTimeDto save(WorkTimeDto workTimeDto);

}
