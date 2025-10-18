package com.airlines.airline_reservation_system.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // <-- CHANGED
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.airlines.airline_reservation_system.entity.Flight;
import com.airlines.airline_reservation_system.entity.Role;
import com.airlines.airline_reservation_system.repository.FlightRepository;
import com.airlines.airline_reservation_system.repository.RoleRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private FlightRepository flightRepository; // <-- CHANGED from FlightService

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed flights if the table is empty
        if (flightRepository.count() == 0) { // <-- CHANGED to use repository
            Flight flight1 = new Flight(null, "AI-202", "DEL", "BOM", "2025-12-10T08:00:00", "2025-12-10T10:00:00", 4500.00, 150);
            Flight flight2 = new Flight(null, "6E-504", "DEL", "BLR", "2025-11-20T07:30:00", "2025-11-20T10:15:00", 5500.00, 120);
            Flight flight3 = new Flight(null, "UK-821", "BOM", "MAA", "2025-11-22T14:00:00", "2025-11-22T15:50:00", 4800.00, 100);

            List<Flight> flights = Arrays.asList(flight1, flight2, flight3); // <-- FIXED: List<Flight> with capital 'F'
            flightRepository.saveAll(flights); // <-- CHANGED to use repository
            System.out.println("--- Initial flights have been seeded to the database. ---");
        }

        // Seed roles if the table is empty
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            
            System.out.println("--- Initial roles have been seeded. ---");
        }
    }
}