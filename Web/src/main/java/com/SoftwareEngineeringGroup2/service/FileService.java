package com.SoftwareEngineeringGroup2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.url-prefix}")
    private String urlPrefix;

    public String uploadFile(MultipartFile file, String type) throws IOException {
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;

        // 创建目录
        String dir = uploadPath + "/" + type;
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 保存文件
        File dest = new File(dir + "/" + filename);
        file.transferTo(dest);

        // 返回访问URL
        return urlPrefix + "/" + type + "/" + filename;
    }
}