package com.montreal.restaurants.restaurant_review.config;

import com.montreal.restaurants.restaurant_review.entity.Restaurant;
import com.montreal.restaurants.restaurant_review.entity.Review;
import com.montreal.restaurants.restaurant_review.entity.User;
import com.montreal.restaurants.restaurant_review.repository.RestaurantRepository;
import com.montreal.restaurants.restaurant_review.repository.ReviewRepository;
import com.montreal.restaurants.restaurant_review.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting data initialization...");

        // Initialize restaurants if database is empty
        if (restaurantRepository.count() == 0) {
            initializeRestaurants();
            System.out.println("✓ Sample restaurants initialized");
        }

        // Initialize test users if none exist
        if (userRepository.count() == 0) {
            initializeUsers();
            System.out.println("✓ Test users initialized");
        }

        // Initialize sample reviews if none exist
        if (reviewRepository.count() == 0) {
            initializeSampleReviews();
            System.out.println("✓ Sample reviews initialized");
        }

        System.out.println("Data initialization completed successfully!");
    }

    /**
     * Initialize famous Montreal restaurants
     */
    private void initializeRestaurants() {

        // some AI generated names

        // Joe Beef - Famous Montreal restaurant
        Restaurant joeBeff = new Restaurant();
        joeBeff.setName("Joe Beef");
        joeBeff.setDescription("An iconic Montreal restaurant known for its exceptional steaks and creative dishes.");
        joeBeff.setAddress("2491 Rue Notre-Dame O, Montreal, QC H3J 1N6");
        joeBeff.setPhone("(514) 935-6504");
        joeBeff.setWebsite("http://joebeef.ca");
        joeBeff.setCuisineType("French");
        joeBeff.setPriceRange("$$$$");
        joeBeff.setLatitude(45.4761);
        joeBeff.setLongitude(-73.5737);
        joeBeff.setAverageRating(4.5);
        joeBeff.setTotalReviews(0);

        // Schwartz's Deli - Montreal institution
        Restaurant schwartz = new Restaurant();
        schwartz.setName("Schwartz's Deli");
        schwartz.setDescription("Montreal's most famous smoked meat deli, serving since 1928.");
        schwartz.setAddress("3895 Boul Saint-Laurent, Montreal, QC H2W 1X9");
        schwartz.setPhone("(514) 842-4813");
        schwartz.setWebsite("http://schwartzsdeli.com");
        schwartz.setCuisineType("Jewish Deli");
        schwartz.setPriceRange("$$");
        schwartz.setLatitude(45.5158);
        schwartz.setLongitude(-73.5844);
        schwartz.setAverageRating(4.2);
        schwartz.setTotalReviews(0);

        // Au Pied de Cochon - Martin Picard's restaurant
        Restaurant auPiedDeCochon = new Restaurant();
        auPiedDeCochon.setName("Au Pied de Cochon");
        auPiedDeCochon.setDescription("Chef Martin Picard's temple to Quebecois cuisine and foie gras.");
        auPiedDeCochon.setAddress("536 Av Duluth E, Montreal, QC H2L 1A9");
        auPiedDeCochon.setPhone("(514) 281-1114");
        auPiedDeCochon.setWebsite("http://restaurantaupieddecochon.ca");
        auPiedDeCochon.setCuisineType("French Canadian");
        auPiedDeCochon.setPriceRange("$$$");
        auPiedDeCochon.setLatitude(45.5200);
        auPiedDeCochon.setLongitude(-73.5800);
        auPiedDeCochon.setAverageRating(4.3);
        auPiedDeCochon.setTotalReviews(0);

        // Toqué! - Fine dining
        Restaurant toque = new Restaurant();
        toque.setName("Toqué!");
        toque.setDescription("Montreal's premier fine dining restaurant with innovative Quebec cuisine.");
        toque.setAddress("900 Place Jean-Paul-Riopelle, Montreal, QC H2Z 2B2");
        toque.setPhone("(514) 499-2084");
        toque.setWebsite("http://restaurant-toque.com");
        toque.setCuisineType("Fine Dining");
        toque.setPriceRange("$$$$");
        toque.setLatitude(45.5088);
        toque.setLongitude(-73.5640);
        toque.setAverageRating(4.6);
        toque.setTotalReviews(0);

        // Bagel shop - St-Viateur
        Restaurant stViateur = new Restaurant();
        stViateur.setName("St-Viateur Bagel");
        stViateur.setDescription("Original Montreal-style bagel shop, wood-fired ovens since 1957.");
        stViateur.setAddress("263 Rue Saint-Viateur O, Montreal, QC H2V 1X5");
        stViateur.setPhone("(514) 276-8044");
        stViateur.setCuisineType("Bakery");
        stViateur.setPriceRange("$");
        stViateur.setLatitude(45.5230);
        stViateur.setLongitude(-73.5960);
        stViateur.setAverageRating(4.4);
        stViateur.setTotalReviews(0);

        // La Banquise - Poutine specialist
        Restaurant laBanquise = new Restaurant();
        laBanquise.setName("La Banquise");
        laBanquise.setDescription("Famous 24/7 poutine restaurant with over 30 varieties.");
        laBanquise.setAddress("994 Rue Rachel E, Montreal, QC H2J 2J3");
        laBanquise.setPhone("(514) 525-2415");
        laBanquise.setCuisineType("Quebecois");
        laBanquise.setPriceRange("$$");
        laBanquise.setLatitude(45.5267);
        laBanquise.setLongitude(-73.5756);
        laBanquise.setAverageRating(4.1);
        laBanquise.setTotalReviews(0);

        // Save all restaurants
        restaurantRepository.save(joeBeff);
        restaurantRepository.save(schwartz);
        restaurantRepository.save(auPiedDeCochon);
        restaurantRepository.save(toque);
        restaurantRepository.save(stViateur);
        restaurantRepository.save(laBanquise);
    }

    /**
     * Initialize test users for development
     */
    private void initializeUsers() {
        // Admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@montreal-restaurants.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setCreatedAt(LocalDateTime.now());

        // Test user 1
        User testUser1 = new User();
        testUser1.setUsername("foodlover");
        testUser1.setEmail("foodlover@email.com");
        testUser1.setPassword(passwordEncoder.encode("password123"));
        testUser1.setCreatedAt(LocalDateTime.now());

        // Test user 2
        User testUser2 = new User();
        testUser2.setUsername("montreal_eats");
        testUser2.setEmail("montreal.eats@email.com");
        testUser2.setPassword(passwordEncoder.encode("password123"));
        testUser2.setCreatedAt(LocalDateTime.now());

        // Test user 3
        User testUser3 = new User();
        testUser3.setUsername("reviewer123");
        testUser3.setEmail("reviewer@email.com");
        testUser3.setPassword(passwordEncoder.encode("password123"));
        testUser3.setCreatedAt(LocalDateTime.now());

        // Save all users
        userRepository.save(admin);
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);
    }

    /**
     * Initialize sample reviews to make the app look populated
     */
    private void initializeSampleReviews() {
        // Wait a moment for restaurants and users to be saved
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get saved entities
        User foodlover = userRepository.findByUsername("foodlover").orElse(null);
        User montrealEats = userRepository.findByUsername("montreal_eats").orElse(null);
        User reviewer = userRepository.findByUsername("reviewer123").orElse(null);

        Restaurant joeBeff = restaurantRepository.findByNameContaining("Joe Beef").get(0);
        Restaurant schwartz = restaurantRepository.findByNameContaining("Schwartz").get(0);
        Restaurant laBanquise = restaurantRepository.findByNameContaining("La Banquise").get(0);

        if (foodlover != null && joeBeff != null) {
            Review review1 = new Review();
            review1.setUser(foodlover);
            review1.setRestaurant(joeBeff);
            review1.setRating(5);
            review1.setComment("Absolutely incredible! The steak was perfectly cooked and the atmosphere is amazing. A must-visit in Montreal!");
            review1.setCreatedAt(LocalDateTime.now().minusDays(5));
            reviewRepository.save(review1);
        }

        if (montrealEats != null && schwartz != null) {
            Review review2 = new Review();
            review2.setUser(montrealEats);
            review2.setRestaurant(schwartz);
            review2.setRating(4);
            review2.setComment("Classic Montreal smoked meat! The line was long but totally worth it. The meat is tender and flavorful.");
            review2.setCreatedAt(LocalDateTime.now().minusDays(3));
            reviewRepository.save(review2);
        }

        if (reviewer != null && laBanquise != null) {
            Review review3 = new Review();
            review3.setUser(reviewer);
            review3.setRestaurant(laBanquise);
            review3.setRating(4);
            review3.setComment("Great poutine selection! Open 24/7 which is perfect for late night cravings. The 'La Banquise' poutine is my favorite.");
            review3.setCreatedAt(LocalDateTime.now().minusDays(1));
            reviewRepository.save(review3);
        }

        // Update restaurant ratings based on reviews
        updateRestaurantRatings();
    }

    /**
     * Update restaurant average ratings and review counts
     */
    private void updateRestaurantRatings() {
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            Double avgRating = reviewRepository.findAverageRatingByRestaurantId(restaurant.getId());
            Long totalReviews = reviewRepository.countByRestaurantId(restaurant.getId());

            if (avgRating != null && totalReviews > 0) {
                restaurant.setAverageRating(Math.round(avgRating * 10.0) / 10.0); // Round to 1 decimal
                restaurant.setTotalReviews(totalReviews.intValue());
                restaurantRepository.save(restaurant);
            }
        }
    }
}
