package com.airlines.airline_reservation_system.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airlines.airline_reservation_system.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}