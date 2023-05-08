package com.example.demo.dao.repository;

import com.example.demo.dao.repository.pojo.MemberAccountDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccountDo, Integer> {
    MemberAccountDo findByUsername(String username);
}
