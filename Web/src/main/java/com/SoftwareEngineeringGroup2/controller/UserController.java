package com.SoftwareEngineeringGroup2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.*;

@RestController
@RequestMapping("/lab1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @Operation(summary = "创建用户", description = "创建新用户")
    public User createUser(
        @RequestParam @Parameter(description = "用户名") String username,
        @RequestParam @Parameter(description = "邮箱") String email
    ) {
        return userService.createUser(username, email);
    }

    @GetMapping("/users")
    @Operation(summary = "获取所有用户", description = "获取所有用户信息")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
