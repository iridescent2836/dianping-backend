package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.config.JwtConfig;
import com.SoftwareEngineeringGroup2.dto.LoginDto;
import com.SoftwareEngineeringGroup2.dto.RegisterDto;
import com.SoftwareEngineeringGroup2.dto.UserInfoDto;
import com.SoftwareEngineeringGroup2.entity.Account;
import com.SoftwareEngineeringGroup2.service.AccountService;
import com.SoftwareEngineeringGroup2.service.AuthService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.SoftwareEngineeringGroup2.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户管理相关接口")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtConfig jwtConfig;
    private final AccountService accountService;
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册接口，支持普通用户和商户注册")
    @ApiOperationSupport(order = 1, author = "软件工程第二组")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto request) {
        User user = userService.register(request);
        Account account = accountService.createAccount(user);
        user.setAccountId(account.getId());
        userService.save(user);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，登录成功返回JWT令牌")
    @ApiOperationSupport(order = 2, author = "软件工程第二组")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto request) {
        // 1. 验证用户凭证
        User user = userService.login(request); // 内部包含密码验证逻辑

        // 2. 创建认证对象
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // 存储加密后的密码
                .roles(user.getRole().name()) // 转换角色为Spring Security格式
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        // 3. 更新安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 生成JWT令牌

        String token = jwtConfig.generateToken(user);

        // 5. 返回响应
        return ResponseEntity.ok(token);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有用户", description = "获取所有用户的信息")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的信息")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    @Operation(summary = "更新当前用户信息", description = "更新当前登录用户的信息")
    public ResponseEntity<User> updateCurrentUser(@RequestBody UserUpdateDto userDto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updateUser(user.getId(), userDto));
    }

    @DeleteMapping("/me")
    @Operation(summary = "删除当前用户", description = "删除当前登录用户的账号")
    public ResponseEntity<Void> deleteCurrentUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户信息", description = "获取指定用户的信息")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "更新用户信息", description = "更新指定用户的信息")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户", description = "删除指定用户的账号")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
