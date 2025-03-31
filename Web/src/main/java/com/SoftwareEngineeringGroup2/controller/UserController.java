package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.config.JwtConfig;
import com.SoftwareEngineeringGroup2.dto.LoginDto;
import com.SoftwareEngineeringGroup2.dto.RegisterDto;
import com.SoftwareEngineeringGroup2.dto.UserInfoDto;
import com.SoftwareEngineeringGroup2.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.SoftwareEngineeringGroup2.config.JwtConfig;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtConfig jwtConfig;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto request) {
        userService.register(request);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/login")
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

}
