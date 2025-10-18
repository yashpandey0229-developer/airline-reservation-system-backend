package com.airlines.airline_reservation_system.controller; // Make sure this package is correct

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airlines.airline_reservation_system.Exception.ResourceNotFoundException;
import com.airlines.airline_reservation_system.entity.Flight;
import com.airlines.airline_reservation_system.repository.FlightRepository;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    // API to add a new flight
    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightRepository.save(flight);
    }

    // API to add multiple flights at once
    @PostMapping("/addMultiple")
    public List<Flight> addMultipleFlights(@RequestBody List<Flight> flights) {
        return flightRepository.saveAll(flights);
    }

    // API to get all flights
    @GetMapping("/all")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // API to get a single flight by its ID
   // API to get a single flight by its ID
@GetMapping("/{id}")
public Flight getFlightById(@PathVariable Long id) {
    return flightRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));
}
    @DeleteMapping("/delete/{id}")
public String deleteFlight(@PathVariable Long id) {
    flightRepository.deleteById(id);
    return "Flight with ID " + id + " has been deleted successfully.";}
    @GetMapping("/search")
public List<Flight> searchFlights(@RequestParam String from, @RequestParam String to) {
    return flightRepository.findByDepartureAirportAndArrivalAirport(from, to);
}
}