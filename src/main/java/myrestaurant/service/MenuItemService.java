package myrestaurant.service;

import myrestaurant.dto.request.menuItem.MenuItemRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.dto.response.pagination.PaginationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface MenuItemService {
    SimpleResponse save(MenuItemRequest menuItemRequest);


    SimpleResponse update(Long menuId, MenuItemRequest menuItemRequest);

    List<MenuItemResponse> getAll();

    SimpleResponse delete(Long menuId);

    List<MenuItemResponse> search(String keyWord);
    List<MenuItemResponse> getAllByOrderByPriceAsc();
    List<MenuItemResponse> getAllByOrderByPriceDesc();
    List<MenuItemResponse> findMenuItemByVegetarianTrueOrFalse(Boolean isTrue);
    PaginationResponse getMenuItemPagination(int page, int size);
}
