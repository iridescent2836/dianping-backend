package com.SoftwareEngineeringGroup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "商店注册信息")
public class StoreRegistrationDto {
    @NotBlank(message = "商店名称不能为空")
    @Size(min = 2, max = 50, message = "商店名称长度必须在2-50个字符之间")
    @Schema(description = "商店名称", example = "示例商店")
    private String name;

    @Size(max = 500, message = "商店描述不能超过500个字符")
    @Schema(description = "商店描述", example = "这是一家示例商店")
    private String description;

    @Schema(description = "商店logo URL", example = "https://example.com/logo.png")
    private String logoUrl;

    @NotBlank(message = "商店地址不能为空")
    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Schema(description = "商店地址", example = "北京市海淀区")
    private String address;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码")
    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @NotNull(message = "注册资金不能为空")
    @Schema(description = "注册资金", example = "10000.00")
    private Double capital;

    @NotBlank(message = "店主身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", message = "请输入正确的身份证号码")
    @Schema(description = "店主身份证号", example = "110101199001011234")
    private String ownerIdCard;

    @Schema(description = "商品类别", example = "[\"酒店\", \"交通\"]")
    private List<String> categories;
}