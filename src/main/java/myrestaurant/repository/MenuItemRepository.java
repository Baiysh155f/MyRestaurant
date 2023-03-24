package myrestaurant.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import myrestaurant.dto.response.menuItem.MenuItemResponse;
import myrestaurant.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.images, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m join m.stopList s where s.date != current date ")
    List<MenuItemResponse> getAllMenuItems();

    List<MenuItemResponse> getAllByStopListNull();

    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.images, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.name ilike concat('%', :keyWord, '%') or m.description " +
            "ilike concat('%', :keyWord, '%') or m.subCategory.name ilike concat('%', :keyWord, '%')" +
            " or m.subCategory.category.name ilike concat('%', :keyWord, '%')")
    List<MenuItemResponse> searchAllByKeyWord(String keyWord);

    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse(m.id,m.name,m.images,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price asc")
    List<MenuItemResponse> getAllByOrderByPriceAsc();

    @Query("select new myrestaurant.dto.response.menuItem.MenuItemResponse(m.id,m.name,m.images,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price desc")
    List<MenuItemResponse> getAllByOrderByPriceDesc();

    @Override
    Page<MenuItem> findAll(Pageable pageable);

}