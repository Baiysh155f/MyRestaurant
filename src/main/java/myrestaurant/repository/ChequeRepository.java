package myrestaurant.repository;

import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque;
import myrestaurant.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new myrestaurant.dto.response.checues.ChequeResponseGrandTotal(c.id, c.createAt," +
            "concat(c.employees.firstName,' ',c.employees.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItem m where c.employees.id = ?1 group by c.id, c.createAt," +
            " c.employees.firstName, c.employees.lastName, m.restaurant.service")
    List<ChequeResponseGrandTotal> getAllChequeByUserId(Long userId);
    @Query("select new myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque(m.id,m.name, m.price, " +
            "count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemsResponseForCheque> getItems(Long chequeId);
}