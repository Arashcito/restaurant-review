package com.montreal.restaurants.restaurant_review.service;

import com.montreal.restaurants.restaurant_review.entity.Restaurant;
import com.montreal.restaurants.restaurant_review.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContaining(name);
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisineType) {
        return restaurantRepository.findByCuisineType(cuisineType);
    }

    public List<Restaurant> getRestaurantsByPriceRange(String priceRange) {
        return restaurantRepository.findByPriceRange(priceRange);
    }

    public List<Restaurant> getRestaurantsByMinRating(Double minRating) {
        return restaurantRepository.findByMinRating(minRating);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void updateRestaurantRating(Long restaurantId, Double newAverageRating, Integer totalReviews) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            Restaurant r = restaurant.get();
            r.setAverageRating(newAverageRating);
            r.setTotalReviews(totalReviews);
            restaurantRepository.save(r);
        }
    }
}
