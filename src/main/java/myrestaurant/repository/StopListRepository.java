package myrestaurant.repository;

import myrestaurant.dto.response.stopList.StopListResponse;
import myrestaurant.entity.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    List<StopListResponse> findByMenuItemId(Long menuId);
}