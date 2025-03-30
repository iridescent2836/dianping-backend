package com.SoftwareEngineeringGroup2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "商店实体类")
@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Schema(description = "商店ID", example = "1")
    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "商店名称", example = "XX旅游商店")
    @Column(name = "store_name", nullable = false, length = 20)
    private String name;

    @Schema(description = "商店简介", example = "提供高质量的旅游服务")
    @Column(name = "store_description", nullable = false, length = 500)
    private String description;

    @Schema(description = "备案地址", example = "北京市海淀区XX街XX号")
    @Column(name = "store_address", nullable = false, length = 100)
    private String address;

    @Schema(description = "注册资金", example = "10000")
    @Column(name = "store_capital", nullable = false)
    private Double capital;

    @Schema(description = "注册时间", example = "2023-01-01")
    @Column(name = "store_register_date", nullable = false)
    private LocalDate registerDate;

    @Schema(description = "店主身份证号", example = "110101199001011234")
    @Column(name = "owner_id_card", nullable = false, length = 18)
    private String ownerIdCard;

    @Schema(description = "商品类别", example = "[\"酒店\", \"交通\"]")
    @ElementCollection
    @CollectionTable(name = "store_categories", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "category")
    private List<String> categories;

    @Schema(description = "商店状态", example = "PENDING")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status = StoreStatus.PENDING;

    public enum StoreStatus {
        PENDING,    // 待审核
        APPROVED,   // 已审核通过
        REJECTED    // 已拒绝
    }

    @Schema(description = "店主用户ID", example = "1")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}