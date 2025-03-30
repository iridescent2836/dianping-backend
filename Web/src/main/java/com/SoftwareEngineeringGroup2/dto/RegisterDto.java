package com.SoftwareEngineeringGroup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import com.SoftwareEngineeringGroup2.entity.User.UserRole;

@Data
@Schema(description = "用户/商户注册请求体") // 类级别描述
public class RegisterDto {
    @NotNull(message = "角色不能为空")
    @Schema(description = "用户角色类型", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "USER", allowableValues = {"USER", "MERCHANT", "ADMIN"})
    private UserRole role;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$", message = "用户名格式错误")
    @Schema(description = "用户名（仅允许字母、数字、下划线，长度3-10）", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "user_123")
    private String username;


    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误")
    @Schema(description = "中国大陆手机号（11位数字）", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "13800138000")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    @Schema(description = "标准邮箱地址", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "user@example.com", format = "email")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d|.*[-_]).{6,32}$", message = "密码需包含字母和数字/符号")
    @Schema(description = "密码（需包含字母和数字/符号，长度6-32）", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Passw0rd!")
    private String password;
}
