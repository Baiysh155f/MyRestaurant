package myrestaurant.service;

import myrestaurant.dto.request.subCategories.SubCategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponsePage;
import myrestaurant.dto.response.subCategories.SubCategoryResponse;
import myrestaurant.entity.SubCategory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface SubCategoryService {
    List<SubCategoryResponse> getAll();

    SimpleResponse save(SubCategoryRequest subCategory);

    SimpleResponse update(Long subCategoryId, SubCategoryRequest subCategory);

    SimpleResponse delete(Long subCategoryId);

    CategoryResponsePage getSubCategoryByCategoryId(Long categoryId);

    List<SubCategoryResponse> findAllSorting(String name);
}
