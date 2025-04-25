package com.SoftwareEngineeringGroup2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Table(name = "product_applications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "商品申请记录")
public class ProductApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "申请ID", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "关联的商品")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    @Schema(description = "申请的商户")
    private User merchant;

    @Schema(description = "申请类型：1-上架申请，2-修改申请", example = "1")
    private Integer type;

    @Schema(description = "申请状态：0-待审核，1-通过，2-拒绝", example = "0")
    private Integer status;

    @Column(length = 500)
    @Schema(description = "审核意见", example = "商品信息完整，同意上架")
    private String comment;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "审核时间")
    private Long reviewTime;
}