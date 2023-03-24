package myrestaurant.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.menuItem.MenuItemRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.dto.response.pagination.PaginationResponse;
import myrestaurant.entity.MenuItem;
import myrestaurant.entity.Restaurant;
import myrestaurant.entity.SubCategory;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.CategoryRepository;
import myrestaurant.repository.MenuItemRepository;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.repository.SubCategoryRepository;
import myrestaurant.service.MenuItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
public class MenuItemServiceIml implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

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
        menuItem.setVegetarian(menuItemRequest.getIsVegetarian());
        SubCategory category = subCategoryRepository.findById(menuItemRequest.getSubCategoryId())
                .orElseThrow(() -> new NotFoundExceptionId("SubCategory this id = " + menuItemRequest.getSubCategoryId() + " not found"));
        menuItem.setSubCategory(category);
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
        menuItem.setVegetarian(menuItemRequest.getIsVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public List<MenuItemResponse> getAll() {
        List<MenuItemResponse> list = new ArrayList<>();
        list.addAll(menuItemRepository.getAllMenuItems());
        list.addAll(menuItemRepository.getAllByStopListNull());
        return list;
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

    @Override
    public List<MenuItemResponse> getAllByOrderByPriceAsc() {
        return menuItemRepository.getAllByOrderByPriceAsc();
    }

    @Override
    public List<MenuItemResponse> getAllByOrderByPriceDesc() {
        return menuItemRepository.getAllByOrderByPriceDesc();
    }

    @Override
    public List<MenuItemResponse> findMenuItemByVegetarianTrueOrFalse(Boolean trueOrFalse) {
            List<MenuItemResponse> menuItems = new ArrayList<>();
            List<MenuItem> items = menuItemRepository.findAll();
            for (MenuItem item : items) {
                if (item.isVegetarian() == trueOrFalse) {
                    menuItems.add(new MenuItemResponse(item.getId(), item.getName(), item.getImages(), item.getPrice(), item.getDescription(), item.isVegetarian()));
                }
            }
            return menuItems;
        }

    @Override
    public PaginationResponse getMenuItemPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<MenuItem> pageManu = menuItemRepository.findAll(pageable);
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setMenuItems(pageManu.getContent());
        paginationResponse.setCurrentPage(pageable.getPageNumber());
        paginationResponse.setPageSize(pageManu.getTotalPages());
        return paginationResponse;
    }

}
