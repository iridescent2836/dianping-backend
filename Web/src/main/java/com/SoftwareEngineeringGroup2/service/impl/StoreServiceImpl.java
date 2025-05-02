package com.SoftwareEngineeringGroup2.service.impl;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.dto.StoreWithOwnerDTO;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.repository.StoreRepository;
import com.SoftwareEngineeringGroup2.repository.UserRepository;
import com.SoftwareEngineeringGroup2.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public Store createStore(StoreRegistrationDto storeDto, User user) {
        if (!user.getRole().equals(User.UserRole.MERCHANT)) {
            throw new RuntimeException("用户不是商户");
        }

        Store store = Store.builder()
                .name(storeDto.getName())
                .description(storeDto.getDescription())
                .logoUrl(storeDto.getLogoUrl())
                .address(storeDto.getAddress())
                .phone(storeDto.getPhone())
                .capital(storeDto.getCapital())
                .registerDate(LocalDateTime.now())
                .ownerIdCard(storeDto.getOwnerIdCard())
                .categories(storeDto.getCategories())
                .ownerId(user.getId())
                .status(Store.StoreStatus.PENDING)
                .build();

        return storeRepository.save(store);
    }

    @Override
    @Transactional
    public Store updateStore(Long id, StoreRegistrationDto storeDto, User user) {
        Store existingStore = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商店不存在"));
        if (!existingStore.getOwnerId().equals(user.getId())) {
            throw new RuntimeException("无权修改此商店");
        }

        existingStore.setName(storeDto.getName());
        existingStore.setDescription(storeDto.getDescription());
        existingStore.setLogoUrl(storeDto.getLogoUrl());
        existingStore.setAddress(storeDto.getAddress());
        existingStore.setPhone(storeDto.getPhone());
        existingStore.setCapital(storeDto.getCapital());
        existingStore.setOwnerIdCard(storeDto.getOwnerIdCard());
        existingStore.setCategories(storeDto.getCategories());

        return storeRepository.save(existingStore);
    }

    @Override
    @Transactional
    public void deleteStore(Long storeId, Long merchantId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("商店不存在"));
        if (!store.getOwnerId().equals(merchantId)) {
            throw new RuntimeException("无权删除此商店");
        }
        storeRepository.delete(store);
    }

    @Override
    @Transactional
    public Store reviewStore(Long storeId, boolean approved, String comment) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("商店不存在"));
        store.setStatus(approved ? Store.StoreStatus.APPROVED : Store.StoreStatus.REJECTED);
        store.setReviewComment(comment);
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getStoresByUserRole(User user) {
        if (user.getRole().equals(User.UserRole.ADMIN)) {
            return storeRepository.findAll();
        } else if (user.getRole().equals(User.UserRole.MERCHANT)) {
            return storeRepository.findByOwnerId(user.getId());
        } else {
            return storeRepository.findByStatus(Store.StoreStatus.APPROVED);
        }
    }

    @Override
    public List<Store> getAllStores(){
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商店不存在"));
    }

    @Override
    public List<Store> getMerchantStores(Long merchantId) {
        return storeRepository.findByOwnerId(merchantId);
    }

    @Override
    public List<Store> getStoresByStatus(Store.StoreStatus status) {
        return storeRepository.findByStatus(status);
    }

    @Override
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("商店不存在"));
    }

    @Override
    public List<StoreWithOwnerDTO> getAllStoresWithOwners() {
        return storeRepository.findAll().stream()
            .map(store -> {
                User owner =userServiceImpl.getUserById(store.getOwnerId());
                return new StoreWithOwnerDTO(
                    store.getId(),
                    store.getName(),
                    store.getDescription(),
                    store.getCategories(),
                    owner.getId(),
                    owner.getUsername()
                );
            })
            .collect(Collectors.toList());
    }

}