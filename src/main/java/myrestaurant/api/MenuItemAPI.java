package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.menuItem.MenuItemRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.dto.response.pagination.PaginationResponse;
import myrestaurant.service.MenuItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@RestController
@RequestMapping("/api/menuItems")
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService menuItemService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/getAll")
    public List<MenuItemResponse> getAll() {
        return menuItemService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.save(menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{menuId}")
    public SimpleResponse update(@PathVariable Long menuId,
                                 @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.update(menuId, menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{menuId}")
    public SimpleResponse delete(@PathVariable Long menuId) {
        return menuItemService.delete(menuId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{keyWord}")
    public List<MenuItemResponse> search(@PathVariable String keyWord) {
        return menuItemService.search(keyWord);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/asc")
    public List<MenuItemResponse> sortAsc(){
        return menuItemService.getAllByOrderByPriceAsc();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/desc")
    public List<MenuItemResponse> sortDesc(){
        return menuItemService.getAllByOrderByPriceDesc();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/vegetarian/{isTrue}")
    public List<MenuItemResponse> vegetarianTrueOrFalse(@PathVariable Boolean isTrue){
        return menuItemService.findMenuItemByVegetarianTrueOrFalse(isTrue);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/pagination")
    public PaginationResponse getManuItemsPagination(@RequestParam int page,
                                                    @RequestParam int size) {
        return menuItemService.getMenuItemPagination(page,size);
    }
}
