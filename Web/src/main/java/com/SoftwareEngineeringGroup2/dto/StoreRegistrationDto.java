package com.SoftwareEngineeringGroup2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "商店注册数据传输对象")
public class StoreRegistrationDto {
    @Schema(description = "商店名称", example = "XX旅游商店")
    @NotBlank(message = "商店名称不能为空")
    @Size(min = 2, max = 20, message = "商店名称长度必须在2-20个字符之间")
    private String shopName;

    @Schema(description = "商店简介", example = "提供高质量的旅游服务")
    @NotBlank(message = "商店简介不能为空")
    @Size(max = 500, message = "商店简介不能超过500个字符")
    private String description;

    @Schema(description = "备案地址", example = "北京市海淀区XX街XX号")
    @NotBlank(message = "备案地址不能为空")
    @Size(max = 100, message = "备案地址不能超过100个字符")
    private String address;

    @Schema(description = "注册资金", example = "10000")
    @NotNull(message = "注册资金不能为空")
    @Min(value = 1000, message = "注册资金不能低于1000")
    private Double capital;

    @Schema(description = "店主身份证号", example = "110101199001011234")
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", message = "请输入有效的身份证号码")
    private String idNumber;

    @Schema(description = "商品类别", example = "[\"旅游\", \"住宿\"]")
    @NotEmpty(message = "商品类别不能为空")
    @Size(min = 1, max = 5, message = "商品类别数量必须在1-5个之间")
    private List<String> categories;

    @Schema(description = "注册时间", example = "2025/03/09")
    @NotNull(message = "注册时间不能为空")
    @JsonFormat(pattern = "yyyy/MM/dd") // 定义日期格式
    private LocalDate registrationDate;
}