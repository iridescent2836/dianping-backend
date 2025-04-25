package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.dto.ProductDto;
import com.SoftwareEngineeringGroup2.entity.Product;
import com.SoftwareEngineeringGroup2.entity.ProductApplication;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.ProductService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "商品管理", description = "商品相关接口，包括商品上架、修改、下架等功能")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/{storeId}")
    @Operation(summary = "上架商品", description = "商户申请上架新商品")
    @ApiOperationSupport(order = 1, author = "软件工程第二组")
    public ResponseEntity<?> createProduct(@PathVariable Long storeId, @RequestBody ProductDto productDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User merchant = (User) authentication.getPrincipal();
        Product product = productService.createProduct(storeId, productDto, merchant);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "修改商品", description = "商户申请修改商品信息")
    @ApiOperationSupport(order = 2, author = "软件工程第二组")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User merchant = (User) authentication.getPrincipal();
        Product product = productService.updateProduct(productId, productDto, merchant);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "下架商品", description = "商户下架商品")
    @ApiOperationSupport(order = 3, author = "软件工程第二组")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User merchant = (User) authentication.getPrincipal();
        productService.deleteProduct(productId, merchant);
        return ResponseEntity.ok("商品已下架");
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "获取商店商品列表", description = "获取指定商店的所有商品")
    @ApiOperationSupport(order = 4, author = "软件工程第二组")
    public ResponseEntity<?> getStoreProducts(@PathVariable Long storeId) {
        List<Product> products = productService.getStoreProducts(storeId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/store/{storeId}/status/{status}")
    @Operation(summary = "获取商店商品列表（按状态）", description = "获取指定商店的指定状态商品")
    @ApiOperationSupport(order = 5, author = "软件工程第二组")
    public ResponseEntity<?> getStoreProductsByStatus(@PathVariable Long storeId, @PathVariable Integer status) {
        List<Product> products = productService.getStoreProductsByStatus(storeId, status);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/applications/pending")
    @Operation(summary = "获取待审核申请", description = "管理员获取待审核的商品申请列表")
    @ApiOperationSupport(order = 6, author = "软件工程第二组")
    public ResponseEntity<?> getPendingApplications() {
        List<ProductApplication> applications = productService.getPendingApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/applications/merchant")
    @Operation(summary = "获取商户申请记录", description = "商户获取自己的商品申请记录")
    @ApiOperationSupport(order = 7, author = "软件工程第二组")
    public ResponseEntity<?> getMerchantApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User merchant = (User) authentication.getPrincipal();
        List<ProductApplication> applications = productService.getMerchantApplications(merchant.getId());
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/applications/{applicationId}/review")
    @Operation(summary = "审核商品申请", description = "管理员审核商品上架/修改申请")
    @ApiOperationSupport(order = 8, author = "软件工程第二组")
    public ResponseEntity<?> reviewApplication(
            @PathVariable Long applicationId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String comment) {
        productService.reviewProduct(applicationId, approved, comment);
        return ResponseEntity.ok("审核完成");
    }
}