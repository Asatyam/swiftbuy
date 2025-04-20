package com.satyamagrawal.swiftbuy.userservice.repository;

import com.satyamagrawal.swiftbuy.userservice.entity.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
