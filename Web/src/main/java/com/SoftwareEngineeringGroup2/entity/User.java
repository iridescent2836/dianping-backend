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
    @Column(name = "user_name")
    private String username;

    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    @Column(name = "user_email")
    private String email;
}
