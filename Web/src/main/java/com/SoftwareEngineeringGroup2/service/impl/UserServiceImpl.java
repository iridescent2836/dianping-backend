package com.SoftwareEngineeringGroup2.service.impl;
import com.SoftwareEngineeringGroup2.dto.LoginDto;
import com.SoftwareEngineeringGroup2.dto.RegisterDto;
import com.SoftwareEngineeringGroup2.dto.UserUpdateDto;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.repository.UserRepository;
import com.SoftwareEngineeringGroup2.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (userDto.getUsername() != null && userDto.getUsername().equals("admin")) {
            throw new RuntimeException("管理员不能修改用户名");
        }

        if (userDto.getUsername() != null && !userDto.getUsername().equals(user.getUsername())) {
            if (existsByUsername(userDto.getUsername())) {
                throw new RuntimeException("用户名已存在");
            }
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            if (existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("邮箱已被使用");
            }
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPhone() != null && !userDto.getPhone().equals(user.getPhone())) {
            if (existsByPhone(userDto.getPhone())) {
                throw new RuntimeException("手机号已被使用");
            }
            user.setPhone(userDto.getPhone());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("用户不存在");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User register(RegisterDto registerDto) {
        // 检查用户名是否已存在
        if (existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("邮箱已被使用");
        }

        // 检查手机号是否已存在
        if (existsByPhone(registerDto.getPhone())) {
            throw new RuntimeException("手机号已被使用");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setRole(registerDto.getRole());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名不存在"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }
}