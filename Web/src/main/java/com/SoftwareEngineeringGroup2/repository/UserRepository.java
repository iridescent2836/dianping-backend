package com.SoftwareEngineeringGroup2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SoftwareEngineeringGroup2.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    /**
     * 通过用户名判断用户是否存在（方法名自动生成查询）
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
}
