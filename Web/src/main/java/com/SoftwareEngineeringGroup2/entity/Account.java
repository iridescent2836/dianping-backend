package com.SoftwareEngineeringGroup2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

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

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @Schema(description = "关联的用户")
    private User user;

    @Column(precision = 10, scale = 2)
    @Schema(description = "账户余额", example = "1000.00")
    private BigDecimal balance;

    @Schema(description = "账户状态：0-正常，1-冻结", example = "0")
    private Integer status;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "更新时间")
    private Long updateTime;
}