package com.SoftwareEngineeringGroup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "商品信息DTO")
public class ProductDto {

    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称不能超过100个字符")
    @Schema(description = "商品名称", example = "示例商品")
    private String name;

    @NotBlank(message = "商品描述不能为空")
    @Size(max = 500, message = "商品描述不能超过500个字符")
    @Schema(description = "商品描述", example = "这是一个示例商品描述")
    private String description;

    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    @Schema(description = "商品价格", example = "99.99")
    private BigDecimal price;

    @NotBlank(message = "商品图片不能为空")
    @Schema(description = "商品图片URL", example = "http://example.com/image.jpg")
    private String imageUrl;
}