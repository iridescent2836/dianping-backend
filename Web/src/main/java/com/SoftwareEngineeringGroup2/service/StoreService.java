package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.dto.StoreRegistrationDto;
import com.SoftwareEngineeringGroup2.entity.Store;
import com.SoftwareEngineeringGroup2.entity.User;

import java.util.List;

public interface StoreService {
    /**
     * 创建新商店
     * @param storeDto 商店注册信息
     * @param owner 店主用户
     * @return 创建的商店实体
     */
    Store createStore(StoreRegistrationDto storeDto, User owner);

    /**
     * 根据用户角色获取商店列表
     * @param user 当前用户
     * @return 商店列表
     */
    List<Store> getStoresByUserRole(User user);

    /**
     * 根据ID获取商店
     * @param id 商店ID
     * @return 商店实体
     */
    Store getStoreById(Long id);

    /**
     * 更新商店信息
     * @param id 商店ID
     * @param storeDto 更新的商店信息
     * @param currentUser 当前用户
     * @return 更新后的商店实体
     */
    Store updateStore(Long id, StoreRegistrationDto storeDto, User currentUser);
}