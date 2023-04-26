package com.example.demo.dao.repository;

import com.example.demo.model.MemberAccountDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccountDo, Long> {

    MemberAccountDo save(MemberAccountDo memberAccountDo);

    Optional<Object> findByUsername(String username);
}
