// Global variables
let restaurants = [];
let currentSection = 'restaurants';

// API base URL
const API_BASE = '/api';

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadRestaurants();
    loadRestaurantOptions();
});

// Navigation functions
function showSection(sectionName) {
    // Hide all sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Remove active class from all nav buttons
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected section
    document.getElementById(sectionName).classList.add('active');
    
    // Add active class to clicked button
    event.target.classList.add('active');
    
    currentSection = sectionName;
}

// Restaurant functions
async function loadRestaurants() {
    const restaurantsList = document.getElementById('restaurantsList');
    restaurantsList.innerHTML = '<div class="loading">Loading restaurants...</div>';
    
    try {
        const response = await fetch(`${API_BASE}/restaurants`);
        if (!response.ok) {
            throw new Error('Failed to load restaurants');
        }
        
        restaurants = await response.json();
        displayRestaurants(restaurants);
    } catch (error) {
        console.error('Error loading restaurants:', error);
        restaurantsList.innerHTML = '<div class="message error">Failed to load restaurants. Please try again.</div>';
    }
}

function displayRestaurants(restaurantsToShow) {
    const restaurantsList = document.getElementById('restaurantsList');
    
    if (restaurantsToShow.length === 0) {
        restaurantsList.innerHTML = '<div class="message">No restaurants found.</div>';
        return;
    }
    
    restaurantsList.innerHTML = restaurantsToShow.map(restaurant => `
        <div class="restaurant-card">
            <div class="restaurant-header">
                <div class="restaurant-name">${restaurant.name}</div>
                <div class="restaurant-cuisine">${restaurant.cuisineType || 'Cuisine not specified'}</div>
                <div class="restaurant-rating">
                    <div class="stars">
                        ${generateStars(restaurant.averageRating)}
                    </div>
                    <span class="rating-text">${restaurant.averageRating || 0} (${restaurant.totalReviews || 0} reviews)</span>
                </div>
            </div>
            <div class="restaurant-body">
                <div class="restaurant-description">${restaurant.description || 'No description available.'}</div>
                <div class="restaurant-details">
                    <div><i class="fas fa-map-marker-alt"></i> ${restaurant.address}</div>
                    ${restaurant.phone ? `<div><i class="fas fa-phone"></i> ${restaurant.phone}</div>` : ''}
                    ${restaurant.website ? `<div><i class="fas fa-globe"></i> <a href="${restaurant.website}" target="_blank">Website</a></div>` : ''}
                    <div class="price-range"><i class="fas fa-dollar-sign"></i> ${restaurant.priceRange || 'Price not specified'}</div>
                </div>
            </div>
        </div>
    `).join('');
}

function generateStars(rating) {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
    
    return '★'.repeat(fullStars) + 
           (hasHalfStar ? '☆' : '') + 
           '☆'.repeat(emptyStars);
}

async function searchRestaurants() {
    const searchTerm = document.getElementById('searchInput').value.trim();
    
    if (!searchTerm) {
        loadRestaurants();
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/restaurants/search?name=${encodeURIComponent(searchTerm)}`);
        if (!response.ok) {
            throw new Error('Search failed');
        }
        
        const searchResults = await response.json();
        displayRestaurants(searchResults);
    } catch (error) {
        console.error('Error searching restaurants:', error);
        showMessage('Search failed. Please try again.', 'error');
    }
}

async function filterByCuisine() {
    const cuisineType = document.getElementById('cuisineFilter').value;
    
    if (!cuisineType) {
        loadRestaurants();
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/restaurants/cuisine/${encodeURIComponent(cuisineType)}`);
        if (!response.ok) {
            throw new Error('Filter failed');
        }
        
        const filteredResults = await response.json();
        displayRestaurants(filteredResults);
    } catch (error) {
        console.error('Error filtering by cuisine:', error);
        showMessage('Filter failed. Please try again.', 'error');
    }
}

async function filterByRating() {
    const minRating = document.getElementById('ratingFilter').value;
    
    if (!minRating) {
        loadRestaurants();
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/restaurants/rating/${minRating}`);
        if (!response.ok) {
            throw new Error('Filter failed');
        }
        
        const filteredResults = await response.json();
        displayRestaurants(filteredResults);
    } catch (error) {
        console.error('Error filtering by rating:', error);
        showMessage('Filter failed. Please try again.', 'error');
    }
}

// Review functions
async function loadRestaurantOptions() {
    const restaurantSelect = document.getElementById('restaurantSelect');
    
    try {
        const response = await fetch(`${API_BASE}/restaurants`);
        if (!response.ok) {
            throw new Error('Failed to load restaurants');
        }
        
        const restaurants = await response.json();
        
        // Clear existing options except the first one
        restaurantSelect.innerHTML = '<option value="">Select a restaurant...</option>';
        
        // Add restaurant options
        restaurants.forEach(restaurant => {
            const option = document.createElement('option');
            option.value = restaurant.id;
            option.textContent = restaurant.name;
            restaurantSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading restaurant options:', error);
    }
}

async function addReview() {
    const restaurantId = document.getElementById('restaurantSelect').value;
    const rating = document.getElementById('rating').value;
    const comment = document.getElementById('comment').value;
    const userId = document.getElementById('userId').value;
    
    if (!restaurantId || !rating || !userId) {
        showMessage('Please fill in all required fields.', 'error');
        return;
    }
    
    const reviewData = {
        restaurantId: parseInt(restaurantId),
        rating: parseInt(rating),
        comment: comment
    };
    
    try {
        const response = await fetch(`${API_BASE}/reviews?userId=${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reviewData)
        });
        
        if (!response.ok) {
            throw new Error('Failed to add review');
        }
        
        const review = await response.json();
        showMessage('Review added successfully!', 'success');
        
        // Clear form
        document.getElementById('restaurantSelect').value = '';
        document.getElementById('rating').value = '';
        document.getElementById('comment').value = '';
        
        // Reload restaurants to update ratings
        loadRestaurants();
        
    } catch (error) {
        console.error('Error adding review:', error);
        showMessage('Failed to add review. Please try again.', 'error');
    }
}

// User registration functions
async function registerUser() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    if (!username || !email || !password) {
        showMessage('Please fill in all fields.', 'error');
        return;
    }
    
    const userData = {
        username: username,
        email: email,
        password: password
    };
    
    try {
        const response = await fetch(`${API_BASE}/users/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        
        if (!response.ok) {
            throw new Error('Registration failed');
        }
        
        const user = await response.json();
        showMessage(`User registered successfully! User ID: ${user.id}`, 'success');
        
        // Clear form
        document.getElementById('username').value = '';
        document.getElementById('email').value = '';
        document.getElementById('password').value = '';
        
    } catch (error) {
        console.error('Error registering user:', error);
        showMessage('Registration failed. Please try again.', 'error');
    }
}

// Utility functions
function showMessage(message, type = 'success') {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    // Create new message
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = message;
    
    // Insert message at the top of the current section
    const currentSection = document.querySelector('.section.active');
    currentSection.insertBefore(messageDiv, currentSection.firstChild);
    
    // Auto-remove message after 5 seconds
    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.remove();
        }
    }, 5000);
}

// Event listeners
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        searchRestaurants();
    }
}); 