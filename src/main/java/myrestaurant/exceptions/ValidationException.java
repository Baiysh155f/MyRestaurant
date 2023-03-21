package myrestaurant.exceptions;

import org.springframework.http.HttpStatus;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
