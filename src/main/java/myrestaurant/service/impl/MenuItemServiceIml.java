package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.menuItem.MenuItemRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.entity.MenuItem;
import myrestaurant.entity.Restaurant;
import myrestaurant.entity.SubCategory;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.MenuItemRepository;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.repository.SubCategoryRepository;
import myrestaurant.service.MenuItemService;
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
public class MenuItemServiceIml implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionId("Restaurant this id = 1 not found"));
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImages(menuItemRequest.getImages());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setRestaurant(restaurant);
        SubCategory category = subCategoryRepository.findById(menuItemRequest.getSubCategoryId())
                .orElseThrow(() -> new NotFoundExceptionId("SubCategory this id = " + menuItemRequest.getSubCategoryId() + " not found"));
        menuItem.setSubCategory(category);
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SAVED...").build();
    }

    @Override
    public SimpleResponse update(Long menuId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundExceptionId("Menu this id = " + menuId + " not found"));
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImages(menuItemRequest.getImages());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public List<MenuItemResponse> getAll() {
        return menuItemRepository.getAll();
    }

    @Override
    public SimpleResponse delete(Long menuId) {
        menuItemRepository.deleteById(menuId);
        return SimpleResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("DELETED...").build();
    }

    @Override
    public List<MenuItemResponse> search(String keyWord) {
        return menuItemRepository.searchAllByKeyWord(keyWord);
    }
}
