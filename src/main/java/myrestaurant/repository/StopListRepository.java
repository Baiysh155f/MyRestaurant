package myrestaurant.repository;

import myrestaurant.dto.response.stopList.StopListResponse;
import myrestaurant.entity.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new myrestaurant.dto.response.stopList.StopListResponse(s.id,s.reason,s.date) from StopList s where s.menuItem.id=?1")
    List<StopListResponse> findByMenuItemId(Long menuId);
}