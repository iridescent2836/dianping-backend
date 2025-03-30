package com.SoftwareEngineeringGroup2.config;

import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.entity.User.UserRole;
import com.SoftwareEngineeringGroup2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AppInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 初始化管理员账号
        initializeAdmin();
    }

    private void initializeAdmin() {
        try {
            // 检查管理员账号是否已存在
            if (authService.findByUsername("admin") == null) {
                logger.info("初始化管理员账号...");

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin114514"))
                        .email("admin@example.com")
                        .phone("13800000000")
                        .role(UserRole.ADMIN)
                        .build();

                authService.saveUser(admin);
                logger.info("管理员账号初始化成功");
            } else {
                logger.info("管理员账号已存在，跳过初始化");
            }
        } catch (Exception e) {
            logger.error("初始化管理员账号失败: {}", e.getMessage(), e);
        }
    }
}