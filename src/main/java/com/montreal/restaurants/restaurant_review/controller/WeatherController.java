package com.montreal.restaurants.restaurant_review.controller;

import com.montreal.restaurants.restaurant_review.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/montreal")
    public ResponseEntity<Map<String, Object>> getMontrealWeather() {
        Map<String, Object> weather = weatherService.getMontrealWeather();
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getWeatherRecommendations() {
        Map<String, Object> recommendations = weatherService.getWeatherRecommendations();
        return ResponseEntity.ok(recommendations);
    }
} 