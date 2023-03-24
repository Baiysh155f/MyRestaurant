package myrestaurant.dto.response.checues;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDailyTotalChequeResponse {
    private String resName;
    private LocalDate date;
    private BigDecimal totalSumDaily;
}
