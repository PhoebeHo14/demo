package com.example.demo.dao.repository;

import com.example.demo.model.MemberAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccountDto, Long> {

    MemberAccountDto save(MemberAccountDto memberAccountDto);

    Optional<Object> findByUsername(String username);
}
