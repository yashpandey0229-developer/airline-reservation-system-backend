package com.airlines.airline_reservation_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airlines.airline_reservation_system.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // This will help us find a role like "ROLE_USER" or "ROLE_ADMIN"
    Optional<Role> findByName(String name);
}