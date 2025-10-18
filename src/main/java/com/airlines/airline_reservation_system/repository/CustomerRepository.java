package com.airlines.airline_reservation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airlines.airline_reservation_system.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}