package myrestaurant.repository;

import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse(m.name,m.images,m.price,m.description,m.isVegetarian) from MenuItem m")
    List<MenuItemResponse> getAll();
    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse" +
            "(m.name, m.images, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.name ilike concat('%', :keyWord, '%') or m.description " +
            "ilike concat('%', :keyWord, '%') or m.subCategory.name ilike concat('%', :keyWord, '%')" +
            " or m.subCategory.category.name ilike concat('%', :keyWord, '%')")
    List<MenuItemResponse> searchAllByKeyWord(String keyWord);
}