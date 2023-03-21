package myrestaurant.dto.response.categories;

import lombok.*;
import myrestaurant.dto.response.subCategories.SubCategoryResponse;
import myrestaurant.entity.SubCategory;

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
public class CategoryResponsePage {
    private String  name;
    private List<SubCategoryResponse> subCategories;
}
