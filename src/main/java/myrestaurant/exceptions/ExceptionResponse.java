package myrestaurant.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;
}
