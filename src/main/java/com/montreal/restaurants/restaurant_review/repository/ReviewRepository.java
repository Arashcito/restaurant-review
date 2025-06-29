package com.montreal.restaurants.restaurant_review.repository;

import com.montreal.restaurants.restaurant_review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByReviewId(Long reviewId);

    /**
     * Calculates the average rating for a restaurant
     * @param restaurantId The ID of the restaurant
     * @return Average rating as Double (null if no reviews exist)
     */
    @Query("SELECT AVG(r.rating) FROM Review r where r.restaurant.id = :restaurantId")
    Double findAverageRating(@Param("restaurantId") Long restaurantId);


    /**
     * Counts the number of reviews for a restaurant
     * @param restaurantId The ID of the restaurant
     * @return Total number of reviews
     */
    @Query("SELECT COUNT(r) FROM Review r WHERE r.restaurant.id = :restaurantId")
    Long countByRestaurantId(@Param("restaurantId") Long restaurantId);


}
