package com.SoftwareEngineeringGroup2.repository;

import com.SoftwareEngineeringGroup2.entity.ProductApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductApplicationRepository extends JpaRepository<ProductApplication, Long> {
    List<ProductApplication> findByMerchantId(Long merchantId);

    List<ProductApplication> findByStatus(Integer status);

    List<ProductApplication> findByMerchantIdAndStatus(Long merchantId, Integer status);
}