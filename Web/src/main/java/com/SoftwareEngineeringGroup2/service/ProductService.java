package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.dto.ProductDto;
import com.SoftwareEngineeringGroup2.entity.*;
import com.SoftwareEngineeringGroup2.repository.ProductApplicationRepository;
import com.SoftwareEngineeringGroup2.repository.ProductRepository;
import com.SoftwareEngineeringGroup2.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductApplicationRepository productApplicationRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Product createProduct(Long storeId, ProductDto productDto, User merchant) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("商店不存在"));

        if (!store.getMerchant().getId().equals(merchant.getId())) {
            throw new RuntimeException("无权操作此商店");
        }

        Product product = Product.builder()
                .store(store)
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .status(0) // 待审核
                .createTime(Instant.now().toEpochMilli())
                .updateTime(Instant.now().toEpochMilli())
                .build();

        product = productRepository.save(product);

        // 创建上架申请
        ProductApplication application = ProductApplication.builder()
                .product(product)
                .merchant(merchant)
                .type(1) // 上架申请
                .status(0) // 待审核
                .createTime(Instant.now().toEpochMilli())
                .build();

        productApplicationRepository.save(application);

        return product;
    }

    @Transactional
    public Product updateProduct(Long productId, ProductDto productDto, User merchant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        if (!product.getStore().getMerchant().getId().equals(merchant.getId())) {
            throw new RuntimeException("无权操作此商品");
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStatus(0); // 待审核
        product.setUpdateTime(Instant.now().toEpochMilli());

        product = productRepository.save(product);

        // 创建修改申请
        ProductApplication application = ProductApplication.builder()
                .product(product)
                .merchant(merchant)
                .type(2) // 修改申请
                .status(0) // 待审核
                .createTime(Instant.now().toEpochMilli())
                .build();

        productApplicationRepository.save(application);

        return product;
    }

    @Transactional
    public void deleteProduct(Long productId, User merchant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        if (!product.getStore().getMerchant().getId().equals(merchant.getId())) {
            throw new RuntimeException("无权操作此商品");
        }

        product.setStatus(2); // 已下架
        product.setUpdateTime(Instant.now().toEpochMilli());
        productRepository.save(product);
    }

    @Transactional
    public void reviewProduct(Long applicationId, boolean approved, String comment) {
        ProductApplication application = productApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("申请记录不存在"));

        Product product = application.getProduct();
        if (approved) {
            product.setStatus(1); // 已上架
            application.setStatus(1); // 通过
        } else {
            product.setStatus(3); // 审核不通过
            application.setStatus(2); // 拒绝
        }

        application.setComment(comment);
        application.setReviewTime(Instant.now().toEpochMilli());

        productRepository.save(product);
        productApplicationRepository.save(application);
    }

    public List<Product> getStoreProducts(Long storeId) {
        return productRepository.findByStoreId(storeId);
    }

    public List<Product> getStoreProductsByStatus(Long storeId, Integer status) {
        return productRepository.findByStoreIdAndStatus(storeId, status);
    }

    public List<ProductApplication> getPendingApplications() {
        return productApplicationRepository.findByStatus(0);
    }

    public List<ProductApplication> getMerchantApplications(Long merchantId) {
        return productApplicationRepository.findByMerchantId(merchantId);
    }
}