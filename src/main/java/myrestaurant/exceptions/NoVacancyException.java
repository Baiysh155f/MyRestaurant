package myrestaurant.exceptions;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
public class NoVacancyException extends RuntimeException {
    public NoVacancyException() {

    }

    public NoVacancyException(String message) {
        super(message);
    }
}
