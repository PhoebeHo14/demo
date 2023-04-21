package com.example.demo.dao.repository;

import com.example.demo.model.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

    public MemberAccount save(MemberAccount memberAccount);
}
