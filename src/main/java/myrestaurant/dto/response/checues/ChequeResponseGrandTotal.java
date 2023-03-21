package myrestaurant.dto.response.checues;

import lombok.*;
import myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque;

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
@NoArgsConstructor
public class ChequeResponseGrandTotal {
    private Long id;
    private LocalDate date;
    private String waiter;
    private List<MenuItemsResponseForCheque> items;
    private BigDecimal averagePrice;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponseGrandTotal(Long id, LocalDate date, String waiter, BigDecimal averagePrice, int service) {
        this.id = id;
        this.date = date;
        this.waiter = waiter;
        this.averagePrice = averagePrice;
        this.service = service;
    }
}
