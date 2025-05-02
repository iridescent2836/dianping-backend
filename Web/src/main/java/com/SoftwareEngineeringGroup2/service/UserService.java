package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.dto.LoginDto;
import com.SoftwareEngineeringGroup2.dto.RegisterDto;
import com.SoftwareEngineeringGroup2.dto.UserUpdateDto;
import com.SoftwareEngineeringGroup2.entity.User;

import java.util.List;

public interface UserService {
    User register(RegisterDto registerDto);

    User login(LoginDto loginDto);

    User getUser(Long userId);

    User updateUser(Long userId, UserUpdateDto userDto);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    User save(User user);

    List<User> getAllUsers();
}