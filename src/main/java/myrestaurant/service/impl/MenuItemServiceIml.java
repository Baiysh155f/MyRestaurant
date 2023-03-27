package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.menuItem.MenuItemRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.dto.response.pagination.PaginationResponse;
import myrestaurant.entity.MenuItem;
import myrestaurant.entity.Restaurant;
import myrestaurant.entity.StopList;
import myrestaurant.entity.SubCategory;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.*;
import myrestaurant.service.MenuItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final StopListRepository stopListRepository;

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
                menuItems.add(new MenuItemResponse(item.getId(),
                        item.getName(),
                        item.getImages(),
                        item.getPrice(),
                        item.getDescription(),
                        item.isVegetarian()));
            }
        }
        return menuItems;
    }

    @Override
    public PaginationResponse findMenuItemsWithPagination(int offset, int pageSize) {
        List<MenuItem> menuItemStopList = new ArrayList<>();
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by("price"));
        Page<MenuItem> allPageable = menuItemRepository.findAll(pageable);
        for (MenuItem menuItem : allPageable) {
            for (StopList stopList : menuItem.getStopList()) {
                if (stopList.getDate().equals(LocalDate.now())) {
                    MenuItem menuItem1 = menuItemRepository.findById(stopList.getMenuItem().getId())
                            .orElseThrow(() -> new NotFoundExceptionId("Menu this id = " + stopList.getMenuItem().getId() + " not found"));
                    menuItemStopList.add(menuItem1);
                }
            }
        }
        List<MenuItemResponse> menuItemResponses = new ArrayList<>(allPageable.stream().
                map(m -> new MenuItemResponse(m.getId(),
                        m.getName(),
                        m.getImages(),
                        m.getPrice(),
                        m.getDescription(),
                        m.isVegetarian())).toList());
        for (MenuItem menuItem : menuItemStopList) {
            menuItemResponses.removeIf(menuItemRespons -> menuItemRespons.getId().equals(menuItem.getId()));
        }
        PaginationResponse response = new PaginationResponse();
        response.setMenuItems(menuItemResponses);
        response.setCurrentPage(allPageable.getNumber());
        response.setPageSize(allPageable.getSize());
        return response;
    }

}
