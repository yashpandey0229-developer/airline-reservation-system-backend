package com.airlines.airline_reservation_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airlines.airline_reservation_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // This custom method will help us find a user by their username
    Optional<User> findByUsername(String username);
}