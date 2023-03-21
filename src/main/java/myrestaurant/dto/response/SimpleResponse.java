package myrestaurant.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Builder
public record SimpleResponse(
        HttpStatus status,
        String message
) {
}
