package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.config.JwtConfig;
import com.SoftwareEngineeringGroup2.dto.LoginDto;
import com.SoftwareEngineeringGroup2.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.SoftwareEngineeringGroup2.repository.UserRepository;
import com.SoftwareEngineeringGroup2.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public void register(RegisterDto request) {
        // 校验密码一致性
//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            throw new IllegalArgumentException("两次密码输入不一致");
//        }

        // 检查用户名唯一性
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 创建用户并加密密码
        User user = new User();
        user.setRole(request.getRole());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    public User login(LoginDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("用户名不存在"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        return user;
    }

    public String generateToken(User user) {
        return jwtConfig.generateToken(user);
    }

}
