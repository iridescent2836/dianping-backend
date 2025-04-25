package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.entity.Account;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.repository.AccountRepository;
import com.SoftwareEngineeringGroup2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public Account createAccount(User user) {
        Account account = Account.builder()
                .userId(user.getId())
                .balance(BigDecimal.ZERO)
                .status(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
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
        account.setUpdateTime(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public Account getAccount(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account getAccountByUsername(String username) {
        // 修改为通过userId查询
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        Account account = accountRepository.findByUserId(user.getId());
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }
        return account;
    }

    @Transactional
    public Account recharge(String username, BigDecimal amount) {
        // 修改为通过userId查询
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        Account account = accountRepository.findByUserId(user.getId());
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }
        if (account.getStatus() == 1) {
            throw new RuntimeException("账户已被冻结");
        }
        account.setBalance(account.getBalance().add(amount));
        account.setUpdateTime(LocalDateTime.now());
        return accountRepository.save(account);
    }
}