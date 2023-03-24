package myrestaurant.dto.response.menuItem;

import lombok.*;

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
@Builder
public class MenuItemResponse{
       private Long id;
       private String name;
       private String images;
       private BigDecimal price;
       private String description;
       private boolean isVegetarian;
}
