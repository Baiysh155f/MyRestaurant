package peaksoft.service.impl;

import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exceptions.NotFoundExceptionId;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantResponse findByIdRestaurant(Long restaurantId) {
        return restaurantRepository.findByIdResponse(restaurantId)
                .orElseThrow(() -> new NotFoundExceptionId("Not found with id =" + restaurantId + "id"));
    }

    @Override
    public void updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundExceptionId("Not found with id =" + restaurantId + "id"));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
