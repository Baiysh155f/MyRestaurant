package myrestaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.categories.CategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponse;
import myrestaurant.entity.Category;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.CategoryRepository;
import myrestaurant.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAll() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<Category> all = categoryRepository.findAll();
        for (Category category : all) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setName(category.getName());
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + categoryId + " not found!!!"));
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest category) {
        Category category1 = new Category();
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SAVED...").build();
    }

    @Override
    public SimpleResponse updateCategory(Long categoryId, CategoryRequest category) {
        Category category1 = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + categoryId + " not found!!!"));
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public SimpleResponse deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK).message("DELETED...").build();
    }
}
