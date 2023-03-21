package myrestaurant.dto.response.checues;

import lombok.*;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque;
import myrestaurant.entity.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
public class ChequeResponseTotal {
    private String WaitersFulName;
    private LocalDate date;
    private Long counterOfCheque;
    private BigDecimal totalSumma;

}
