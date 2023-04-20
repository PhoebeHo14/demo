package com.example.demo.dao;

import com.example.demo.entity.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

    public MemberAccount save(MemberAccount memberAccount);
}
