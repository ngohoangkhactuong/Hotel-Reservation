package com.khactuong.hotel.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khactuong.hotel.model.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);

    boolean existsByName(String role);
}
