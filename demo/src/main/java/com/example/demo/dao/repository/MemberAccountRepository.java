package com.example.demo.dao.repository;

import com.example.demo.model.MemberAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccountDto, Long> {

    public MemberAccountDto save(MemberAccountDto memberAccountDto);
}
