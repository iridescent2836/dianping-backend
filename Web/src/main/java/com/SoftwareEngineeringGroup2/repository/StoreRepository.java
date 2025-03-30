package com.SoftwareEngineeringGroup2.repository;

import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.Store.StoreStatus;
import com.SoftwareEngineeringGroup2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByStatus(StoreStatus status);

    List<Store> findByOwner(User owner);
}