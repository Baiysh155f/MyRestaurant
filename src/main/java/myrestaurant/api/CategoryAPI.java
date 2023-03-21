package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.request.categories.CategoryRequest;
import myrestaurant.dto.response.categories.CategoryResponse;
import myrestaurant.entity.Category;
import myrestaurant.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryAPI {
    private final CategoryService categoryService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping
    private List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @GetMapping("/{categoryId}")
    public Category findByIdCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping
    public SimpleResponse saveCategory(@RequestBody CategoryRequest category) {
        return categoryService.saveCategory(category);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{categoryId}")
    public SimpleResponse updateCategory(@PathVariable Long categoryId,
                                         @RequestBody CategoryRequest category) {
        return categoryService.updateCategory(categoryId, category);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{categoryId}")
    public SimpleResponse deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteById(categoryId);
    }

}
