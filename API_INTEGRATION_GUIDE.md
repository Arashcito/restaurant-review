# 🚀 API Integration Guide for Restaurant Review App

## 📋 Overview

Your restaurant review application now includes **external API integrations** to fetch real data and enhance the user experience. This guide explains how to set up and use these APIs.

## 🔑 Available APIs

### 1. **Google Places API** 🌍
- **Purpose**: Fetch real restaurant data from Google Maps
- **Features**: Restaurant details, ratings, photos, reviews
- **Cost**: Free tier available (limited requests)

### 2. **OpenWeather API** 🌤️
- **Purpose**: Get weather data for Montreal
- **Features**: Current weather, temperature, conditions
- **Cost**: Free tier available (1000 requests/day)

## 🛠️ Setup Instructions

### Step 1: Get Google Places API Key

1. **Go to Google Cloud Console**: https://console.cloud.google.com/
2. **Create a new project** or select existing one
3. **Enable Places API**:
   - Go to "APIs & Services" → "Library"
   - Search for "Places API"
   - Click "Enable"
4. **Create API Key**:
   - Go to "APIs & Services" → "Credentials"
   - Click "Create Credentials" → "API Key"
   - Copy the generated key
5. **Restrict the key** (recommended):
   - Click on the API key
   - Under "Application restrictions" select "HTTP referrers"
   - Add your domain (for production)

### Step 2: Get OpenWeather API Key

1. **Go to OpenWeather**: https://openweathermap.org/api
2. **Sign up for free account**
3. **Get API Key**:
   - Go to "My API Keys"
   - Copy your default key
4. **Wait for activation** (may take a few hours)

### Step 3: Configure Your Application

1. **Update `application.properties`**:
```properties
# Replace with your actual API keys
google.places.api.key=YOUR_ACTUAL_GOOGLE_PLACES_API_KEY
openweather.api.key=YOUR_ACTUAL_OPENWEATHER_API_KEY
```

2. **Restart your application**:
```bash
./mvnw spring-boot:run
```

## 🎯 New API Endpoints

### Google Places API Endpoints

#### Search Restaurants
```http
GET /api/restaurants/api/search?query=pizza
```
**Response**: List of restaurants from Google Places

#### Get Restaurant Details
```http
GET /api/restaurants/api/details/{placeId}
```
**Response**: Detailed restaurant information

### Weather API Endpoints

#### Get Montreal Weather
```http
GET /api/weather/montreal
```
**Response**:
```json
{
  "temperature": 22.5,
  "feels_like": 24.0,
  "humidity": 65,
  "condition": "Clear",
  "description": "clear sky",
  "city": "Montreal"
}
```

#### Get Weather-Based Recommendations
```http
GET /api/weather/recommendations
```
**Response**:
```json
{
  "weather": { ... },
  "recommendations": {
    "cuisine": "Variety of cuisines, Outdoor dining",
    "restaurants": "Terrace restaurants, Cafes, Bistros",
    "reason": "Pleasant weather for outdoor dining",
    "outdoor": "Consider outdoor dining options"
  }
}
```

## 🧪 Testing Your APIs

### Test Google Places API
```bash
# Search for pizza restaurants
curl "http://localhost:8080/api/restaurants/api/search?query=pizza"

# Search for Italian restaurants
curl "http://localhost:8080/api/restaurants/api/search?query=italian"
```

### Test Weather API
```bash
# Get current weather
curl "http://localhost:8080/api/weather/montreal"

# Get weather recommendations
curl "http://localhost:8080/api/weather/recommendations"
```

## 🔄 How It Works

### Fallback Mechanism
- **If API key is missing**: Returns mock data
- **If API call fails**: Returns mock data
- **Always functional**: Your app works even without API keys

### Mock Data
When APIs are unavailable, you get realistic sample data:
- **Restaurants**: Joe Beef, Schwartz's Deli
- **Weather**: Montreal weather simulation

## 💡 Usage Examples

### Frontend Integration

Add this to your `script.js`:

```javascript
// Fetch restaurants from Google Places
async function searchGooglePlaces(query) {
    try {
        const response = await fetch(`/api/restaurants/api/search?query=${query}`);
        const restaurants = await response.json();
        displayRestaurants(restaurants);
    } catch (error) {
        console.error('Error fetching from Google Places:', error);
    }
}

// Get weather recommendations
async function getWeatherRecommendations() {
    try {
        const response = await fetch('/api/weather/recommendations');
        const data = await response.json();
        displayWeatherRecommendations(data);
    } catch (error) {
        console.error('Error fetching weather:', error);
    }
}
```

### Weather-Based Recommendations

The weather API provides intelligent restaurant recommendations:

- **Cold weather (< 0°C)**: Hot soup, comfort food
- **Cool weather (0-15°C)**: Hearty meals, stews
- **Pleasant weather (15-25°C)**: Outdoor dining, variety
- **Hot weather (> 25°C)**: Light meals, salads

## 🚨 Important Notes

### API Limits
- **Google Places**: 1000 requests/day (free tier)
- **OpenWeather**: 1000 requests/day (free tier)

### Security
- **Never commit API keys** to version control
- **Use environment variables** in production
- **Restrict API keys** to your domain

### Error Handling
- All API calls include error handling
- Fallback to mock data ensures app functionality
- Check console logs for API errors

## 🎓 Learning Benefits

This integration demonstrates:
- **External API consumption**
- **Error handling and fallbacks**
- **Async programming**
- **JSON parsing**
- **HTTP client usage**
- **Configuration management**

## 📈 Next Steps

1. **Get API keys** and test the endpoints
2. **Integrate with frontend** for enhanced UX
3. **Add more APIs** (Yelp, TripAdvisor, etc.)
4. **Implement caching** for better performance
5. **Add rate limiting** to respect API limits

## 🆘 Troubleshooting

### Common Issues

1. **API key not working**:
   - Check if API is enabled in Google Cloud Console
   - Verify key restrictions
   - Wait for OpenWeather key activation

2. **No data returned**:
   - Check console logs for errors
   - Verify API key in application.properties
   - Test API endpoints directly

3. **Rate limiting**:
   - Check API usage in respective dashboards
   - Implement caching if needed

### Debug Commands
```bash
# Check if app is running
curl http://localhost:8080/api/restaurants

# Test weather API
curl http://localhost:8080/api/weather/montreal

# Check application logs
tail -f logs/application.log
```

---

**🎉 Congratulations!** Your restaurant review app now has real-world API integrations that will make it much more impressive for your resume and portfolio! 