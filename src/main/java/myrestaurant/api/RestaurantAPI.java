package myrestaurant.api;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.restaurant.RestaurantRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.restaurant.RestaurantResponse;
import myrestaurant.service.impl.RestaurantServiceImpl;
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
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantAPI {
    private final RestaurantServiceImpl restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping
    public List<RestaurantResponse> getAll() {
        return restaurantService.getAllRestaurant();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{restaurantId}")
    public RestaurantResponse getByIdRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.findByIdRestaurant(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{restaurantId}")
    public SimpleResponse updateRestaurant(@PathVariable Long restaurantId,
                                           @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.updateRestaurant(restaurantId, restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{restaurantId}")
    public SimpleResponse deleteRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.deleteRestaurantById(restaurantId);
    }
}
