package myrestaurant.dto.request.menuItem;

import jakarta.persistence.Column;
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
public class MenuItemRequest {
    private String name;
    private String images;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;
    private Long subCategoryId;
}
