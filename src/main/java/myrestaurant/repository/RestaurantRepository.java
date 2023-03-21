package myrestaurant.repository;

import myrestaurant.dto.response.restaurant.RestaurantResponse;
import myrestaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new myrestaurant.dto.response.restaurant.RestaurantResponse(r.id,r.name,r.location,r.restType,r.service) from Restaurant r where r.id =?1")
    Optional<RestaurantResponse> findByIdResponse(Long restaurantId);

    @Query("select new myrestaurant.dto.response.restaurant.RestaurantResponse(r.id,r.name,r.location,r.restType,r.service) from Restaurant r")
    List<RestaurantResponse> findAllRestaurant();
}