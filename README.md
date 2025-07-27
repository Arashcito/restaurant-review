# Montreal Restaurant Reviews

A Spring Boot application for reviewing restaurants in Montreal with a modern web interface.

## Features

- **Restaurant Management**: Browse, search, and filter restaurants
- **Review System**: Add reviews with ratings and comments
- **User Registration**: Simple user registration system
- **Modern UI**: Responsive web interface with search and filtering
- **REST API**: Complete REST API for all operations

## Technology Stack

- **Backend**: Spring Boot 3.5.3, Spring Data JPA, Spring Security
- **Database**: H2 in-memory database (for development)
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./mvnw spring-boot:run
```

4. Open your browser and go to `http://localhost:8080`

### API Endpoints

#### Restaurants
- `GET /api/restaurants` - Get all restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `GET /api/restaurants/search?name={name}` - Search restaurants by name
- `GET /api/restaurants/cuisine/{cuisineType}` - Filter by cuisine type
- `GET /api/restaurants/rating/{minRating}` - Filter by minimum rating
- `GET /api/restaurants/price/{priceRange}` - Filter by price range

#### Reviews
- `GET /api/reviews/restaurant/{restaurantId}` - Get reviews for a restaurant
- `GET /api/reviews/user/{userId}` - Get reviews by user
- `POST /api/reviews?userId={userId}` - Add a new review

#### Users
- `POST /api/users/register` - Register a new user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username

### Sample Data

The application comes with sample data including:

- **6 Montreal Restaurants**: Joe Beef, Schwartz's Deli, Au Pied de Cochon, Toqué!, St-Viateur Bagel, La Banquise
- **4 Test Users**: admin, foodlover, montreal_eats, reviewer123
- **Sample Reviews**: Pre-populated reviews for testing

### Database Console

Access the H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Project Structure

```
src/
├── main/
│   ├── java/com/montreal/restaurants/restaurant_review/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data transfer objects
│   │   ├── entity/         # JPA entities
│   │   ├── exception/      # Custom exceptions
│   │   ├── repository/     # Data repositories
│   │   └── service/        # Business logic
│   └── resources/
│       ├── static/         # Frontend files (HTML, CSS, JS)
│       └── application.properties
└── test/                   # Test files
```

## Features in Detail

### Frontend Features
- **Responsive Design**: Works on desktop and mobile devices
- **Search & Filter**: Search by name, filter by cuisine and rating
- **Modern UI**: Clean, modern interface with smooth animations
- **Interactive Forms**: User registration and review submission
- **Real-time Updates**: Dynamic content loading via AJAX

### Backend Features
- **RESTful API**: Complete REST API with proper HTTP methods
- **Data Validation**: Input validation with custom error messages
- **Exception Handling**: Global exception handler with proper error responses
- **Security**: Basic security configuration with CORS support
- **Database**: JPA entities with proper relationships

## Development

### Adding New Features

1. **New Entity**: Create entity class in `entity/` package
2. **Repository**: Create repository interface in `repository/` package
3. **Service**: Add business logic in `service/` package
4. **Controller**: Create REST endpoints in `controller/` package
5. **Frontend**: Update HTML/JS for new features

### Testing

Run tests with:
```bash
./mvnw test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is for educational purposes.

## Contact

For questions or issues, please open an issue on GitHub. 