package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface RestaurantService {
    void saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse findByIdRestaurant(Long restaurantId);
    void updateRestaurant(Long restaurantId,RestaurantRequest restaurantRequest);

    void deleteRestaurantById(Long restaurantId);
}
