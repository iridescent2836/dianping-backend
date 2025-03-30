package com.SoftwareEngineeringGroup2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户实体类")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Schema(description = "用户ID", example = "1")
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "用户名", example = "zhangsan")
    @Column(name = "user_name", unique = true, nullable = false, length = 10)
    private String username;

    @Schema(description = "用户密码", example = "password123")
    @Column(name = "user_password", nullable = false)
    private String password;

    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    @Column(name = "user_email", nullable = false)
    private String email;
    
    @Schema(description = "用户手机号", example = "13800138000")
    @Column(name = "user_phone", nullable = false, length = 11)
    private String phone;

    @Schema(description = "用户角色", example = "USER")
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // 用户角色枚举
    public enum UserRole {
        ADMIN, // 管理员
        USER,  // 普通用户
        MERCHANT // 商户
    }
}
