package com.SoftwareEngineeringGroup2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SoftwareEngineeringGroup2.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

}
