package com.montreal.restaurants.restaurant_review.service;

import com.montreal.restaurants.restaurant_review.dto.ReviewDto;
import com.montreal.restaurants.restaurant_review.entity.Restaurant;
import com.montreal.restaurants.restaurant_review.entity.Review;
import com.montreal.restaurants.restaurant_review.entity.User;
import com.montreal.restaurants.restaurant_review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    public Review addReview(ReviewDto reviewDto, Long userId) {
        Optional<User> user = userService.findById(userId);
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(reviewDto.getRestaurantId());

        if (user.isEmpty() || restaurant.isEmpty()) {
            throw new RuntimeException("User or Restaurant not found");
        }

        Review review = new Review(user.get(), restaurant.get(), reviewDto.getComment(), reviewDto.getRating());

        Review savedReview = reviewRepository.save(review);

        // Update restaurant rating
        updateRestaurantAverageRating(reviewDto.getRestaurantId());

        return savedReview;
    }

    public List<Review> getReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    private void updateRestaurantAverageRating(Long restaurantId) {
        Double averageRating = reviewRepository.findAverageRatingByRestaurantId(restaurantId);
        Long totalReviews = reviewRepository.countByRestaurantId(restaurantId);

        restaurantService.updateRestaurantRating(
                restaurantId,
                averageRating != null ? averageRating : 0.0,
                totalReviews.intValue()
        );
    }
}