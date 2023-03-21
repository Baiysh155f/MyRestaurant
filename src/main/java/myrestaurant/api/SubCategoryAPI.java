package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.subCategories.SubCategoryRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.categories.CategoryResponsePage;
import myrestaurant.dto.response.subCategories.SubCategoryResponse;
import myrestaurant.service.SubCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@RestController
@RequestMapping("/api/subCategories")
@RequiredArgsConstructor
public class SubCategoryAPI {
    private final SubCategoryService subCategoryService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping
    public List<SubCategoryResponse> getAll() {
        return subCategoryService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody SubCategoryRequest subCategory) {
        return subCategoryService.save(subCategory);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{subCategoryId}")
    public SimpleResponse update(@PathVariable Long subCategoryId,
                                 @RequestBody SubCategoryRequest subCategory) {
       return subCategoryService.update(subCategoryId, subCategory);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{subCategoryId}")
    public SimpleResponse delete(@PathVariable Long subCategoryId){
        return subCategoryService.delete(subCategoryId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @GetMapping("/{categoryId}/getSubCategoryByCategoryID")
    public CategoryResponsePage getSubCategoryByCategoryId(@PathVariable Long categoryId){
        return subCategoryService.getSubCategoryByCategoryId(categoryId, Sort.by("name"));
    }
}
