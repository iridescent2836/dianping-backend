package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.dto.StoreWithOwnerDTO;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;

import java.util.List;

public interface StoreService {
    Store createStore(StoreRegistrationDto storeDto, User user);

    Store updateStore(Long id, StoreRegistrationDto storeDto, User user);

    void deleteStore(Long storeId, Long merchantId);

    Store reviewStore(Long storeId, boolean approved, String comment);

    List<Store> getStoresByUserRole(User user);

    Store getStoreById(Long id);

    List<Store> getMerchantStores(Long merchantId);

    List<Store> getStoresByStatus(Store.StoreStatus status);

    List<Store> getAllStores();

    Store getStore(Long storeId);

    List<StoreWithOwnerDTO> getAllStoresWithOwners();
}