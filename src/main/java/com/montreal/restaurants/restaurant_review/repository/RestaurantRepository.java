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

    /**
     * SELECT * FROM restaurants WHERE google_place_id = ?
     * Returns: One restaurant (wrapped in Optional - might be empty)
     * */
    Optional<Restaurant> findByGooglePlaceId(String googlePlaceId);


    /**
     * SELECT * FROM restaurants WHERE cuisine_type = ?
     * Returns: List of matching restaurants
    * */
    List<Restaurant> findByCuisineType(String cuisineType);


    List<Restaurant> findByPriceRange(String priceRange);

    /**
     * Searches for names containing the text (e.g., "bur" finds "McDonald's Burger")
     * % symbols mean "any characters before/after"
     * */
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name%")
    List<Restaurant> findByNameContaining(@Param("name") String name);


    /**
     * Finds restaurants with minimum star rating
     * Orders results from best-rated to worst
     * */
    @Query("SELECT r FROM Restaurant r WHERE r.averageRating >= :minRating ORDER BY r.averageRating DESC")
    List<Restaurant> findByMinRating(@Param("minRating") Double minRating);
}