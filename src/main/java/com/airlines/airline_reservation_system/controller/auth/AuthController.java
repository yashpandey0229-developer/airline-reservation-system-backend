package com.airlines.airline_reservation_system.controller.auth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airlines.airline_reservation_system.entity.Role;
import com.airlines.airline_reservation_system.entity.User;
import com.airlines.airline_reservation_system.repository.RoleRepository;
import com.airlines.airline_reservation_system.repository.UserRepository;
import com.airlines.airline_reservation_system.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
private JwtUtil jwtUtil; // <-- ADD THIS

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

   
    @PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
    User user = userRepository.findByUsername(loginDetails.getUsername()).orElse(null);

    if (user != null && passwordEncoder.matches(loginDetails.getPassword(), user.getPassword())) {
        // If login is successful, generate a JWT
        String token = jwtUtil.generateToken(user.getUsername());

        // Return the token in the response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    return ResponseEntity.status(401).body("Error: Invalid username or password");
}
}