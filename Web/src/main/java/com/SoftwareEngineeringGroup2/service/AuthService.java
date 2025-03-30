package com.SoftwareEngineeringGroup2.service;

import com.SoftwareEngineeringGroup2.entity.User;

public interface AuthService {
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    User findByUsername(String username);

    /**
     * 保存用户信息
     * 
     * @param user 用户对象
     * @return 保存后的用户对象
     */
    User saveUser(User user);

    /**
     * 获取当前登录用户
     * 
     * @return 当前登录的用户对象
     */
    User getCurrentUser();

    // 保存当前登录用户到进程上下文中
}