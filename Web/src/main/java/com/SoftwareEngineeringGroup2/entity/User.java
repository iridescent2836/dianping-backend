package com.SoftwareEngineeringGroup2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Schema(description = "用户实体类", title = "用户信息")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Schema(description = "用户ID，系统自动生成", example = "1", requiredMode = Schema.RequiredMode.AUTO)
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "用户名（3-10个字符，只能包含字母、数字和下划线）", example = "zhangsan", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_name", unique = true, nullable = false, length = 10)
    private String username;

    @Schema(description = "用户密码（6-32个字符，需包含字母和数字）", example = "Password123", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_password", nullable = false)
    private String password;

    @Schema(description = "用户邮箱（标准邮箱格式）", example = "zhangsan@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_email", nullable = false)
    private String email;

    @Schema(description = "用户手机号（11位中国大陆手机号）", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_phone", nullable = false, length = 11)
    private String phone;

    @Schema(description = "用户角色（ADMIN-管理员，USER-普通用户，MERCHANT-商户）", example = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Schema(description = "用户账户信息")
    @Column(name = "account_id")
    private Long accountId;

    @Schema(hidden = true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Schema(hidden = true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Schema(hidden = true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Schema(hidden = true)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Schema(description = "用户角色枚举", title = "用户角色")
    public enum UserRole {
        @Schema(description = "管理员")
        ADMIN,
        @Schema(description = "普通用户")
        USER,
        @Schema(description = "商户")
        MERCHANT
    }

    @Schema(hidden = true)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
    }
}
