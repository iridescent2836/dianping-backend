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
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "商品信息")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "商品ID", example = "1")
    private Long id;

    @Column(name = "store_id", nullable = false)
    @Schema(description = "所属商店ID")
    private Long storeId;

    @Column(length = 100)
    @Schema(description = "商品名称", example = "示例商品")
    private String name;

    @Column(length = 500)
    @Schema(description = "商品描述", example = "这是一个示例商品描述")
    private String description;

    @Column(precision = 10, scale = 2)
    @Schema(description = "商品价格", example = "99.99")
    private BigDecimal price;

    @Column(length = 255)
    @Schema(description = "商品图片URL", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "商品状态：0-待审核，1-已上架，2-已下架，3-审核不通过", example = "0")
    private Integer status;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}