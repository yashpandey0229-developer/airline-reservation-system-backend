package com.airlines.airline_reservation_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airlines.airline_reservation_system.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
List<Flight> findByDepartureAirportAndArrivalAirport(String departure, String arrival);
}



    
    

