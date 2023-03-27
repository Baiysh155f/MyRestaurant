package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.subCategories.SubCategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponsePage;
import myrestaurant.dto.response.subCategories.SubCategoryResponse;
import myrestaurant.entity.Category;
import myrestaurant.entity.SubCategory;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.CategoryRepository;
import myrestaurant.repository.SubCategoryRepository;
import myrestaurant.service.SubCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategoryResponse> getAll() {
        return subCategoryRepository.getAll();
    }

    @Override
    public SimpleResponse save(SubCategoryRequest subCategory) {
        Category category = categoryRepository.findById(subCategory.getCategoryId())
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + subCategory.getCategoryId() + " not found!!!"));
        SubCategory subCategory1 = new SubCategory();
        subCategory1.setName(subCategory.getName());
        subCategory1.setCategory(category);
        subCategoryRepository.save(subCategory1);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SAVED...").build();
    }

    @Override
    public SimpleResponse update(Long subCategoryId, SubCategoryRequest subCategory) {
        SubCategory subCategory1 = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + subCategoryId + " not found!!!"));
        Category category = categoryRepository.findById(subCategory.getCategoryId())
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + subCategory.getCategoryId() + " not found!!!"));
        subCategory1.setName(subCategory.getName());
        subCategory1.setCategory(category);
        subCategoryRepository.save(subCategory1);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public SimpleResponse delete(Long subCategoryId) {
        SubCategory category = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundExceptionId("This SubCategory = " + subCategoryId + " not found!!!"));
        subCategoryRepository.delete(category);
        return SimpleResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("DELETED...").build();
    }

    @Override
    public CategoryResponsePage getSubCategoryByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundExceptionId("This category = " + categoryId + " not found!!!"));
        List<SubCategoryResponse> subCategoryByCategoryId =
                subCategoryRepository.getSubCategoryByOrderByNameAsAndByCategoryId(category.getId());
        return CategoryResponsePage.builder()
                .name(category.getName())
                .subCategories(subCategoryByCategoryId)
                .build();
    }

    @Override
    public List<SubCategoryResponse> findAllSorting(String name) {
        return subCategoryRepository.getAll(Sort.by(name));
    }
}
