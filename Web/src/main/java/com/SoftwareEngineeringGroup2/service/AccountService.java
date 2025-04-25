package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.entity.Account;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(User user) {
        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .status(0)
                .createTime(Instant.now().toEpochMilli())
                .updateTime(Instant.now().toEpochMilli())
                .build();
        return accountRepository.save(account);
    }

    @Transactional
    public Account recharge(Long userId, BigDecimal amount) {
        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }
        if (account.getStatus() == 1) {
            throw new RuntimeException("账户已被冻结");
        }
        account.setBalance(account.getBalance().add(amount));
        account.setUpdateTime(Instant.now().toEpochMilli());
        return accountRepository.save(account);
    }

    public Account getAccount(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findByUserUsername(username);
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }
        return account;
    }

    @Transactional
    public Account recharge(String username, BigDecimal amount) {
        Account account = accountRepository.findByUserUsername(username);
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }
        if (account.getStatus() == 1) {
            throw new RuntimeException("账户已被冻结");
        }
        account.setBalance(account.getBalance().add(amount));
        account.setUpdateTime(Instant.now().toEpochMilli());
        return accountRepository.save(account);
    }
}