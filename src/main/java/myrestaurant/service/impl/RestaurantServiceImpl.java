package myrestaurant.service.impl;

import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.entity.Employees;
import myrestaurant.enums.Role;
import myrestaurant.repository.EmployeesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import myrestaurant.dto.request.restaurant.RestaurantRequest;
import myrestaurant.dto.response.restaurant.RestaurantResponse;
import myrestaurant.entity.Restaurant;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.service.RestaurantService;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final EmployeesRepository employeesRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 EmployeesRepository employeesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.employeesRepository = employeesRepository;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurant.setNumberOfEmployees(1);
        Employees employees = employeesRepository.findByRole(Role.ADMIN)
                .orElseThrow(() -> new NotFoundExceptionId("Not found Admin"));
        employees.setRestaurant(restaurant);
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SAVED...").build();
    }

    @Override
    public RestaurantResponse findByIdRestaurant(Long restaurantId) {
        return restaurantRepository.findByIdResponse(restaurantId)
                .orElseThrow(() -> new NotFoundExceptionId("Not found with id =" + restaurantId + "id"));
    }

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        return restaurantRepository.findAllRestaurant();
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundExceptionId("Not found with id =" + restaurantId + "id"));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurant.setLocation(restaurantRequest.getLocation());
        Employees byEmail = employeesRepository.findByEmail("admin2gmail.com")
                .orElseThrow(() -> new NotFoundExceptionId("Not found this email id"));
        restaurant.getEmployees().add(byEmail);
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public SimpleResponse deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED...").build();
    }
}
