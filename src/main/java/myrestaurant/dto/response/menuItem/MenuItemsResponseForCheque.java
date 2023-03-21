package myrestaurant.dto.response.menuItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemsResponseForCheque {
   private Long id;
   private String name;
   private BigDecimal price;
   private Long amount;
}
