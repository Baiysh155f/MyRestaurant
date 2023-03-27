package myrestaurant.service;

import myrestaurant.dto.request.categories.CategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponse;
import myrestaurant.dto.response.categories.CategoryResponsePage;
import myrestaurant.entity.Category;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
public interface CategoryService {
    List<CategoryResponse> getAll();

    CategoryResponsePage getById(Long categoryId);

    SimpleResponse saveCategory(CategoryRequest category);

    SimpleResponse updateCategory(Long categoryId, CategoryRequest category);

    SimpleResponse deleteById(Long categoryId);
}
