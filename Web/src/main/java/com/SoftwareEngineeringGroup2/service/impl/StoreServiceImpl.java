package com.SoftwareEngineeringGroup2.service.impl;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;
import com.SoftwareEngineeringGroup2.service.StoreService;
import com.SoftwareEngineeringGroup2.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    private void validateCategories(List<String> categories) {
        List<String> validCategories = List.of("酒店", "交通", "餐饮", "景区门票");
        for (String category : categories) {
            if (!validCategories.contains(category)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "无效的商品类别：" + category + "。有效类别为：酒店、交通、餐饮、景区门票");
            }
        }
    }

    @Override
    public Store createStore(StoreRegistrationDto storeDto, User owner) {
        validateCategories(storeDto.getCategories());

        Store store = Store.builder()
                .name(storeDto.getName())
                .description(storeDto.getDescription())
                .address(storeDto.getAddress())
                .capital(storeDto.getCapital())
                .registerDate(LocalDate.now())
                .ownerIdCard(storeDto.getOwnerIdCard())
                .owner(owner)
                .categories(storeDto.getCategories())
                .status(Store.StoreStatus.PENDING)
                .build();

        return storeRepository.save(store);
    }

    @Override
    public List<Store> getStoresByUserRole(User user) {
        if (user.getRole().equals("ADMIN")) {
            // 管理员可以查看所有商店
            return storeRepository.findAll();
        } else if (user.getRole().equals("MERCHANT")) {
            // 商户只能查看自己的商店
            return storeRepository.findByOwner(user);
        } else {
            // 普通用户只能查看已审核通过的商店
            return storeRepository.findByStatus(Store.StoreStatus.APPROVED);
        }
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商店不存在"));
    }

    @Override
    public Store updateStore(Long id, StoreRegistrationDto storeDto, User currentUser) {
        Store store = getStoreById(id);
        
        // 验证权限：只有店主或管理员可以修改店铺信息
        if (!store.getOwner().equals(currentUser) && !currentUser.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("没有权限修改该商店信息");
        }

        validateCategories(storeDto.getCategories());

        store.setName(storeDto.getName());
        store.setDescription(storeDto.getDescription());
        store.setAddress(storeDto.getAddress());
        store.setCapital(storeDto.getCapital());
        store.setOwnerIdCard(storeDto.getOwnerIdCard());
        store.setCategories(storeDto.getCategories());

        return storeRepository.save(store);
    }
}