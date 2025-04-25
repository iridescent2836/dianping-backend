package com.SoftwareEngineeringGroup2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户账户信息")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "账户ID", example = "1")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Schema(description = "用户ID")
    private Long userId;

    @Column(precision = 10, scale = 2)
    @Schema(description = "账户余额", example = "1000.00")
    private BigDecimal balance;

    @Schema(description = "账户状态：0-正常，1-冻结", example = "0")
    private Integer status;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}