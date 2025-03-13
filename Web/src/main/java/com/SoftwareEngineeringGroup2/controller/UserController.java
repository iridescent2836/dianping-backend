package com.SoftwareEngineeringGroup2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    @Operation(summary = "获取用户", description = "获取指定用户信息")
    @Parameter(name = "id", description = "用户ID")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @Operation(summary = "创建用户", description = "创建新用户")
    @Parameter(name = "username", description = "用户名")
    @Parameter(name = "email", description = "邮箱")
    public User createUser(@PathVariable String username, @PathVariable String email) {
        return userService.createUser(username, email);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "删除用户", description = "删除指定用户")
    @Parameter(name = "id", description = "用户ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users")
    @Operation(summary = "获取所有用户", description = "获取所有用户信息")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
