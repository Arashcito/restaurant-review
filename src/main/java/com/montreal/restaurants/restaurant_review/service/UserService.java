package com.montreal.restaurants.restaurant_review.service;

import com.montreal.restaurants.restaurant_review.dto.UserRegistrationDto;
import com.montreal.restaurants.restaurant_review.entity.User;
import com.montreal.restaurants.restaurant_review.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDto registrationDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User(
                registrationDto.getUsername(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword())
        );

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}
