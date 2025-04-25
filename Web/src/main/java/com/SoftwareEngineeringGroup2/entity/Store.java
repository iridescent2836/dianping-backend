package com.SoftwareEngineeringGroup2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @NotBlank(message = "商店名称不能为空")
    @Size(min = 2, max = 50, message = "商店名称长度必须在2-50个字符之间")
    @Column(name = "store_name", nullable = false, length = 50)
    @Schema(description = "商店名称", example = "示例商店")
    private String name;

    @Size(max = 500, message = "商店描述不能超过500个字符")
    @Column(name = "store_description", columnDefinition = "TEXT")
    @Schema(description = "商店描述", example = "这是一家示例商店")
    private String description;

    @NotBlank(message = "商店地址不能为空")
    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Column(name = "store_address", nullable = false, length = 200)
    @Schema(description = "商店地址", example = "北京市海淀区")
    private String address;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码")
    @Column(name = "store_phone", nullable = false, length = 11)
    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @NotNull(message = "注册资金不能为空")
    @Column(name = "store_capital", nullable = false)
    @Schema(description = "注册资金", example = "10000.00")
    private Double capital;

    @NotNull(message = "注册时间不能为空")
    @Column(name = "store_register_date", nullable = false)
    @Schema(description = "注册时间")
    private LocalDateTime registerDate;

    @NotBlank(message = "店主身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", message = "请输入正确的身份证号码")
    @Column(name = "owner_id_card", nullable = false, length = 18)
    @Schema(description = "店主身份证号", example = "110101199001011234")
    private String ownerIdCard;

    @ElementCollection
    @CollectionTable(name = "store_categories", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "category")
    @Schema(description = "商品类别", example = "[\"酒店\", \"交通\"]")
    private List<String> categories;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    @Schema(description = "商店状态")
    private StoreStatus status;

    @Column(name = "review_comment", columnDefinition = "TEXT")
    @Schema(description = "审核意见")
    private String reviewComment;

    @Schema(description = "店主用户ID", example = "1")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    @Schema(description = "所属商户")
    private User merchant;

    @Column(name = "logo_url")
    @Schema(description = "商店logo URL", example = "https://example.com/logo.png")
    private String logoUrl;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @Schema(description = "商店商品列表")
    private List<Product> products;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (status == null) {
            status = StoreStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }

    public enum StoreStatus {
        @Schema(description = "待审核")
        PENDING,
        @Schema(description = "已通过")
        APPROVED,
        @Schema(description = "已拒绝")
        REJECTED
    }
}