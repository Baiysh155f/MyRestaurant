package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.service.MenuItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping
@RequiredArgsConstructor
public class JavaPAI {
    private final MenuItemService menuItemService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/asc")
    public List<MenuItemResponse> sortAsc() {
        return menuItemService.getAllByOrderByPriceAsc();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/desc")
    public List<MenuItemResponse> sortDesc() {
        return menuItemService.getAllByOrderByPriceDesc();
    }
}
