package com.SoftwareEngineeringGroup2.controller;

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
<<<<<<< HEAD
import java.util.List;
import io.swagger.v3.oas.annotations.*;
import org.springframework.web.server.ResponseStatusException;
=======
>>>>>>> c552b265eee1546dfe3cee66cf248a75aa54085b

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto request) {
        userService.register(request);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto request) {
        System.out.println(request.getUsername());
        User user = userService.login(request);
        return ResponseEntity.ok("登录成功，用户角色: " + user.getRole());
    }



}
