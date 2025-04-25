package com.SoftwareEngineeringGroup2.repository;

import com.SoftwareEngineeringGroup2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId);

    List<Product> findByStoreIdAndStatus(Long storeId, Integer status);

    List<Product> findByStatus(Integer status);
}