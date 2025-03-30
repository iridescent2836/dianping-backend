package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.AuthService;
import com.SoftwareEngineeringGroup2.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "商店管理", description = "商店相关接口")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @Operation(summary = "创建商店", description = "商户创建新商店")
    @PreAuthorize("hasAuthority('MERCHANT')")
    public ResponseEntity<Store> createStore(@Valid @RequestBody StoreRegistrationDto storeDto) {
        User currentUser = authService.getCurrentUser();
        Store store = storeService.createStore(storeDto, currentUser);
        return ResponseEntity.ok(store);
    }

    @GetMapping
    @Operation(summary = "获取商店列表", description = "获取商店列表，根据用户角色返回不同结果")
    public ResponseEntity<List<Store>> getStores() {
        User currentUser = authService.getCurrentUser();
        List<Store> stores = storeService.getStoresByUserRole(currentUser);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商店详情", description = "根据ID获取商店详情")
    public ResponseEntity<Store> getStoreById(@PathVariable @Parameter(description = "商店ID") Long id) {
        Store store = storeService.getStoreById(id);
        return ResponseEntity.ok(store);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商店信息", description = "更新商店信息，只有店主或管理员可以修改")
    @PreAuthorize("hasAnyAuthority('MERCHANT', 'ADMIN')")
    public ResponseEntity<Store> updateStore(
            @PathVariable @Parameter(description = "商店ID") Long id,
            @Valid @RequestBody StoreRegistrationDto storeDto) {
        User currentUser = authService.getCurrentUser();
        Store store = storeService.updateStore(id, storeDto, currentUser);
        return ResponseEntity.ok(store);
    }
}