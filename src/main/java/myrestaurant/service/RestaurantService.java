package myrestaurant.service;

import myrestaurant.dto.request.restaurant.RestaurantRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.restaurant.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse findByIdRestaurant(Long restaurantId);
    List<RestaurantResponse> getAllRestaurant();
    SimpleResponse updateRestaurant(Long restaurantId,RestaurantRequest restaurantRequest);

    SimpleResponse deleteRestaurantById(Long restaurantId);
}
