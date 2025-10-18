package com.airlines.airline_reservation_system.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airlines.airline_reservation_system.entity.Booking;
import com.airlines.airline_reservation_system.entity.Customer;
import com.airlines.airline_reservation_system.entity.Flight;
import com.airlines.airline_reservation_system.repository.BookingRepository;
import com.airlines.airline_reservation_system.repository.CustomerRepository;
import com.airlines.airline_reservation_system.repository.FlightRepository;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long flightId, @RequestParam Long customerId, @RequestParam int seats) {

        // Find the flight and customer from the database
        Flight flight = flightRepository.findById(flightId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (flight == null || customer == null) {
            return ResponseEntity.badRequest().body("Invalid Flight or Customer ID.");
        }

        // Check if enough seats are available
        if (flight.getAvailableSeats() < seats) {
            return ResponseEntity.badRequest().body("Sorry, not enough seats available.");
        }

        // If seats are available, update the flight's seat count
        flight.setAvailableSeats(flight.getAvailableSeats() - seats);
        flightRepository.save(flight);

        // Create and save the new booking
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setFlight(flight);
        newBooking.setNumberOfSeats(seats);
        newBooking.setBookingDate(LocalDateTime.now());
        newBooking.setStatus("CONFIRMED");

        Booking savedBooking = bookingRepository.save(newBooking);

        return ResponseEntity.ok(savedBooking);
        // Add this inside the BookingController class


}
// Add this inside the BookingController class

@GetMapping("/all")
public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
}
// Add this new method inside the BookingController

@DeleteMapping("/cancel/{bookingId}")
@Transactional
public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
    // Find the booking
    Booking booking = bookingRepository.findById(bookingId).orElse(null);

    if (booking == null) {
        return ResponseEntity.badRequest().body("Booking not found with ID: " + bookingId);
    }

    // Get the associated flight
    Flight flight = booking.getFlight();

    // Add the seats back to the flight's available seats
    flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
    flightRepository.save(flight); // Save the updated flight details

    // Delete the booking
    bookingRepository.delete(booking);

    return ResponseEntity.ok("Booking with ID " + bookingId + " has been successfully canceled.");
}
    }

