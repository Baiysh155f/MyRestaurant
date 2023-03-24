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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesDailyTotalResponse {
    private String employeeName;
    private LocalDate date;
    private BigDecimal allSum;

}
