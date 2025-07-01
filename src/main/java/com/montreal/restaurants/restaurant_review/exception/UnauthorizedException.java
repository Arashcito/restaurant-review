package com.montreal.restaurants.restaurant_review.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(){
        super("you are not authorized to perform this action");
    }
}
