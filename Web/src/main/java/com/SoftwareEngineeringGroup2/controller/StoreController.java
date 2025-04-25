package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "商店管理", description = "商店管理相关接口")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    @Operation(summary = "创建商店", description = "商户创建新商店")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Store> createStore(@RequestBody StoreRegistrationDto storeDto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }

    @PutMapping("/{storeId}")
    @Operation(summary = "更新商店", description = "商户更新商店信息")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Store> updateStore(@PathVariable Long storeId, @RequestBody StoreRegistrationDto storeDto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(storeService.updateStore(storeId, storeDto, user));
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "删除商店", description = "商户删除商店")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId, @AuthenticationPrincipal User user) {
        storeService.deleteStore(storeId, user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{storeId}/review")
    @Operation(summary = "审核商店", description = "管理员审核商店申请")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Store> reviewStore(@PathVariable Long storeId, @RequestParam boolean approved,
            @RequestParam(required = false) String comment) {
        return ResponseEntity.ok(storeService.reviewStore(storeId, approved, comment));
    }

    @GetMapping("/merchant/{merchantId}")
    @Operation(summary = "获取商户商店列表", description = "获取指定商户的所有商店")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ResponseEntity<List<Store>> getMerchantStores(@PathVariable Long merchantId) {
        return ResponseEntity.ok(storeService.getMerchantStores(merchantId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "获取状态商店列表", description = "获取指定状态的商店列表")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Store>> getStoresByStatus(@PathVariable Store.StoreStatus status) {
        return ResponseEntity.ok(storeService.getStoresByStatus(status));
    }

    @GetMapping("/{storeId}")
    @Operation(summary = "获取商店详情", description = "获取指定商店的详细信息")
    public ResponseEntity<Store> getStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStore(storeId));
    }
}