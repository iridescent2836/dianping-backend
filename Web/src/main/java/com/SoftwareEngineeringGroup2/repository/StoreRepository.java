package com.SoftwareEngineeringGroup2.repository;

import com.SoftwareEngineeringGroup2.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByOwnerId(Long merchantId);

    List<Store> findByStatus(Store.StoreStatus status);

    List<Store> findAll();
}