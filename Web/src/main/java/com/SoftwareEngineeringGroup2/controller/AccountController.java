package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.entity.Account;
import com.SoftwareEngineeringGroup2.service.AccountService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "账户管理", description = "账户相关接口，包括账户查询、充值等功能")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/info")
    @Operation(summary = "获取账户信息", description = "获取当前用户的账户信息")
    @ApiOperationSupport(order = 1, author = "软件工程第二组")
    public ResponseEntity<?> getAccountInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.getAccountByUsername(username);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/recharge")
    @Operation(summary = "账户充值", description = "为当前用户的账户充值")
    @ApiOperationSupport(order = 2, author = "软件工程第二组")
    public ResponseEntity<?> recharge(@RequestParam BigDecimal amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.recharge(username, amount);
        return ResponseEntity.ok(account);
    }
}