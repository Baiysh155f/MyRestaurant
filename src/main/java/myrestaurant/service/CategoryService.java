package myrestaurant.service;

import myrestaurant.dto.request.categories.CategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponse;
import myrestaurant.entity.Category;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
public interface CategoryService {
    List<CategoryResponse> getAll();

    Category getById(Long categoryId);

    SimpleResponse saveCategory(CategoryRequest category);

    SimpleResponse updateCategory(Long categoryId, CategoryRequest category);

    SimpleResponse deleteById(Long categoryId);
}
