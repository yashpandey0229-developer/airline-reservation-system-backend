package com.airlines.airline_reservation_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths in your backend
                .allowedOrigins("http://localhost:5173") // Allow your React app's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD") // Allow these HTTP methods
                .allowCredentials(true);
    }
}
