package com.montreal.restaurants.restaurant_review.repository;

import com.montreal.restaurants.restaurant_review.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// @Repository : Marks this as a Spring Data repository
//extends JpaRepository<User, Long>: Inherits common CRUD operations where:
//User is your entity class
//Long is the type of the primary key (ID)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

/**
 * By extending JpaRepository, you automatically get these methods without writing any implementation:
 *
 *
 * save(User user)          // Create or update
 * findById(Long id)        // Read
 * findAll()                // Get all users
 * deleteById(Long id)      // Delete
 * count()                  // Get total users
 * // ...and many more
 * */

/**
 * When you call findByUsername(String username):
 *
 * Spring makes this SQL: SELECT * FROM users WHERE username = 'username'
 * */