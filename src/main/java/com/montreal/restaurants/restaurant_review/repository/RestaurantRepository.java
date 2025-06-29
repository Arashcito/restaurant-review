package com.montreal.restaurants.restaurant_review.repository;

import com.montreal.restaurants.restaurant_review.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByGooglePlaceId(String googlePlaceId);

    List<Restaurant> findByCuisineType(String cuisineType);

    List<Restaurant> findByPriceRange(String priceRange);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name%")
    List<Restaurant> findByNameContaining(@Param("name") String name);

    @Query("SELECT r FROM Restaurant r WHERE r.averageRating >= :minRating ORDER BY r.averageRating DESC")
    List<Restaurant> findByMinRating(@Param("minRating") Double minRating);
}