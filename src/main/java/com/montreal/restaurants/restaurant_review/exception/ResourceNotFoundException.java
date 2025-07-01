package com.montreal.restaurants.restaurant_review.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("Resource %s not found for field %s", resource, field, value));
    }
}
