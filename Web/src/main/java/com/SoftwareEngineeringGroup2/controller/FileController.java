package com.SoftwareEngineeringGroup2.controller;

import com.SoftwareEngineeringGroup2.service.FileService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Tag(name = "文件上传", description = "文件上传相关接口")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload/product")
    @Operation(summary = "上传商品图片", description = "上传商品图片，返回图片URL")
    @ApiOperationSupport(order = 1, author = "软件工程第二组")
    public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileService.uploadFile(file, "products");
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件上传失败：" + e.getMessage());
        }
    }

    @PostMapping("/upload/store")
    @Operation(summary = "上传商店Logo", description = "上传商店Logo，返回图片URL")
    @ApiOperationSupport(order = 2, author = "软件工程第二组")
    public ResponseEntity<?> uploadStoreLogo(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileService.uploadFile(file, "stores");
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件上传失败：" + e.getMessage());
        }
    }
}