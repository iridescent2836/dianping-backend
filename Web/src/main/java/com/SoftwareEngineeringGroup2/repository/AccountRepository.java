package com.SoftwareEngineeringGroup2.repository;

import com.SoftwareEngineeringGroup2.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserId(Long userId);

    Account findByUserUsername(String username);
}