package com.SoftwareEngineeringGroup2.dto;

import com.SoftwareEngineeringGroup2.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息响应体")
public class UserInfoDto {
    @Schema(description = "用户角色", example = "ADMIN")
    private User.UserRole role;

    @Schema(description = "用户名", example = "admin")
    private String name;

    @Schema(description = "用户电话", example = "ADMIN")
    private String phone;

    @Schema(description = "用户邮箱", example = "123")
    private String email;
}
