package com.montreal.restaurants.restaurant_review.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${openweather.api.key:}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Get current weather in Montreal
     */
    public Map<String, Object> getMontrealWeather() {
        if (apiKey == null || apiKey.isEmpty()) {
            return getMockWeather();
        }

        try {
            String url = String.format("/weather?q=Montreal,CA&appid=%s&units=metric", apiKey);
            
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseWeatherResponse)
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching weather: " + e.getMessage());
            return getMockWeather();
        }
    }

    /**
     * Get weather-based restaurant recommendations
     */
    public Map<String, Object> getWeatherRecommendations() {
        Map<String, Object> weather = getMontrealWeather();
        Map<String, Object> recommendations = new HashMap<>();
        
        double temperature = (Double) weather.get("temperature");
        String condition = (String) weather.get("condition");
        
        recommendations.put("weather", weather);
        recommendations.put("recommendations", generateRecommendations(temperature, condition));
        
        return recommendations;
    }

    private Map<String, Object> parseWeatherResponse(String response) {
        Map<String, Object> weather = new HashMap<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            
            weather.put("temperature", root.get("main").get("temp").asDouble());
            weather.put("feels_like", root.get("main").get("feels_like").asDouble());
            weather.put("humidity", root.get("main").get("humidity").asInt());
            weather.put("condition", root.get("weather").get(0).get("main").asText());
            weather.put("description", root.get("weather").get(0).get("description").asText());
            weather.put("city", root.get("name").asText());
            
        } catch (Exception e) {
            System.err.println("Error parsing weather response: " + e.getMessage());
            return getMockWeather();
        }
        
        return weather;
    }

    private Map<String, Object> generateRecommendations(double temperature, String condition) {
        Map<String, Object> recommendations = new HashMap<>();
        
        if (temperature < 0) {
            recommendations.put("cuisine", "Hot soup, Comfort food, Warm beverages");
            recommendations.put("restaurants", "Hot pot, Pho, Ramen, Coffee shops");
            recommendations.put("reason", "Cold weather calls for warm, comforting food");
        } else if (temperature < 15) {
            recommendations.put("cuisine", "Hearty meals, Stews, Hot dishes");
            recommendations.put("restaurants", "Steakhouses, Italian, French bistros");
            recommendations.put("reason", "Cool weather perfect for hearty meals");
        } else if (temperature < 25) {
            recommendations.put("cuisine", "Variety of cuisines, Outdoor dining");
            recommendations.put("restaurants", "Terrace restaurants, Cafes, Bistros");
            recommendations.put("reason", "Pleasant weather for outdoor dining");
        } else {
            recommendations.put("cuisine", "Light meals, Salads, Cold beverages");
            recommendations.put("restaurants", "Salad bars, Smoothie shops, Ice cream");
            recommendations.put("reason", "Hot weather calls for light, refreshing food");
        }
        
        if (condition.toLowerCase().contains("rain")) {
            recommendations.put("indoor", "Perfect for cozy indoor dining");
        } else if (condition.toLowerCase().contains("snow")) {
            recommendations.put("indoor", "Great for warm, comforting meals indoors");
        } else {
            recommendations.put("outdoor", "Consider outdoor dining options");
        }
        
        return recommendations;
    }

    private Map<String, Object> getMockWeather() {
        Map<String, Object> weather = new HashMap<>();
        weather.put("temperature", 22.5);
        weather.put("feels_like", 24.0);
        weather.put("humidity", 65);
        weather.put("condition", "Clear");
        weather.put("description", "clear sky");
        weather.put("city", "Montreal");
        return weather;
    }
} 