package myrestaurant.dto.request.subCategories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myrestaurant.entity.Category;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequest {
    private String name;
    private Long categoryId;
}
