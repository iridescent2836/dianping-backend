package com.SoftwareEngineeringGroup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户信息响应体")
public class StoreWithOwnerDTO {
    @Schema(description = "店铺id", example = "1")
    private Long shopId;

    @Schema(description = "店铺名", example = "shop")
    private String shopName;

    @Schema(description = "店铺描述", example = "shop")
    private String description;

    @Schema(description = "店铺描述", example = "shop")
    private List<String> categories;

    @Schema(description = "店铺拥有者id", example = "1")
    private Long ownerId;

    @Schema(description = "店铺拥有者用户名", example = "admin")
    private String ownerUsername;

    public StoreWithOwnerDTO(Long shopId, String shopName, String description,List<String> categories, Long ownerId, String ownerUsername) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.description = description;
        this.categories = categories;
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
    }

    // getters/setters


}
