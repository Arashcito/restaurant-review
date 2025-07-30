package com.montreal.restaurants.restaurant_review.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montreal.restaurants.restaurant_review.entity.Restaurant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class GooglePlacesService {

    @Value("${google.places.api.key:}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GooglePlacesService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Search for restaurants in Montreal using Google Places API
     */
    public List<Restaurant> searchRestaurantsInMontreal(String query) {
        if (apiKey == null || apiKey.isEmpty()) {
            return getMockRestaurants();
        }

        try {
            String url = String.format("/textsearch/json?query=%s+restaurants+Montreal&key=%s", 
                    query, apiKey);
            
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseRestaurantsFromResponse)
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching from Google Places API: " + e.getMessage());
            return getMockRestaurants();
        }
    }

    /**
     * Get restaurant details by place ID
     */
    public Restaurant getRestaurantDetails(String placeId) {
        if (apiKey == null || apiKey.isEmpty()) {
            return getMockRestaurant();
        }

        try {
            String url = String.format("/details/json?place_id=%s&fields=name,formatted_address,formatted_phone_number,website,rating,user_ratings_total,photos,opening_hours&key=%s", 
                    placeId, apiKey);
            
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseRestaurantDetails)
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching restaurant details: " + e.getMessage());
            return getMockRestaurant();
        }
    }

    private List<Restaurant> parseRestaurantsFromResponse(String response) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.get("results");
            
            if (results != null && results.isArray()) {
                for (JsonNode result : results) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(result.get("name").asText());
                    restaurant.setAddress(result.get("formatted_address").asText());
                    restaurant.setGooglePlaceId(result.get("place_id").asText());
                    
                    if (result.has("rating")) {
                        restaurant.setAverageRating(result.get("rating").asDouble());
                    }
                    
                    if (result.has("user_ratings_total")) {
                        restaurant.setTotalReviews(result.get("user_ratings_total").asInt());
                    }
                    
                    // Extract phone number if available
                    if (result.has("formatted_phone_number")) {
                        restaurant.setPhone(result.get("formatted_phone_number").asText());
                    }
                    
                    // Extract website if available
                    if (result.has("website")) {
                        restaurant.setWebsite(result.get("website").asText());
                    }
                    
                    // Set cuisine type based on types array
                    if (result.has("types")) {
                        JsonNode types = result.get("types");
                        String cuisineType = determineCuisineType(types);
                        restaurant.setCuisineType(cuisineType);
                    }
                    
                    // Set price range based on price_level
                    if (result.has("price_level")) {
                        int priceLevel = result.get("price_level").asInt();
                        restaurant.setPriceRange(convertPriceLevel(priceLevel));
                    }
                    
                    restaurants.add(restaurant);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing Google Places response: " + e.getMessage());
        }
        
        return restaurants.isEmpty() ? getMockRestaurants() : restaurants;
    }

    private Restaurant parseRestaurantDetails(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.get("result");
            
            if (result != null) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(result.get("name").asText());
                restaurant.setAddress(result.get("formatted_address").asText());
                
                if (result.has("formatted_phone_number")) {
                    restaurant.setPhone(result.get("formatted_phone_number").asText());
                }
                
                if (result.has("website")) {
                    restaurant.setWebsite(result.get("website").asText());
                }
                
                if (result.has("rating")) {
                    restaurant.setAverageRating(result.get("rating").asDouble());
                }
                
                if (result.has("user_ratings_total")) {
                    restaurant.setTotalReviews(result.get("user_ratings_total").asInt());
                }
                
                return restaurant;
            }
        } catch (Exception e) {
            System.err.println("Error parsing restaurant details: " + e.getMessage());
        }
        
        return getMockRestaurant();
    }

    private String determineCuisineType(JsonNode types) {
        for (JsonNode type : types) {
            String typeStr = type.asText().toLowerCase();
            if (typeStr.contains("restaurant")) {
                if (typeStr.contains("french")) return "French";
                if (typeStr.contains("italian")) return "Italian";
                if (typeStr.contains("chinese")) return "Chinese";
                if (typeStr.contains("japanese")) return "Japanese";
                if (typeStr.contains("indian")) return "Indian";
                if (typeStr.contains("mexican")) return "Mexican";
                if (typeStr.contains("pizza")) return "Pizza";
                if (typeStr.contains("burger")) return "Burgers";
                if (typeStr.contains("seafood")) return "Seafood";
                if (typeStr.contains("steakhouse")) return "Steakhouse";
            }
        }
        return "Restaurant";
    }

    private String convertPriceLevel(int priceLevel) {
        switch (priceLevel) {
            case 1: return "$";
            case 2: return "$$";
            case 3: return "$$$";
            case 4: return "$$$$";
            default: return "$$";
        }
    }

    // Mock data when API is not available
    private List<Restaurant> getMockRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        
        Restaurant r1 = new Restaurant();
        r1.setName("Joe Beef");
        r1.setDescription("An iconic Montreal restaurant known for its exceptional steaks and creative dishes.");
        r1.setAddress("2491 Rue Notre-Dame O, Montreal, QC H3J 1N6");
        r1.setPhone("(514) 935-6504");
        r1.setWebsite("http://joebeef.ca");
        r1.setCuisineType("French");
        r1.setPriceRange("$$$$");
        r1.setAverageRating(4.8);
        r1.setTotalReviews(1250);
        restaurants.add(r1);

        Restaurant r2 = new Restaurant();
        r2.setName("Schwartz's Deli");
        r2.setDescription("Montreal's most famous smoked meat deli, serving since 1928.");
        r2.setAddress("3895 Boul Saint-Laurent, Montreal, QC H2W 1X9");
        r2.setPhone("(514) 842-4813");
        r2.setWebsite("http://schwartzsdeli.com");
        r2.setCuisineType("Jewish Deli");
        r2.setPriceRange("$$");
        r2.setAverageRating(4.5);
        r2.setTotalReviews(2100);
        restaurants.add(r2);

        return restaurants;
    }

    private Restaurant getMockRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Sample Restaurant");
        restaurant.setDescription("A sample restaurant for testing purposes.");
        restaurant.setAddress("123 Sample St, Montreal, QC");
        restaurant.setPhone("(514) 123-4567");
        restaurant.setCuisineType("International");
        restaurant.setPriceRange("$$");
        restaurant.setAverageRating(4.0);
        restaurant.setTotalReviews(100);
        return restaurant;
    }
} 