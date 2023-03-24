package myrestaurant.dto.response.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import myrestaurant.dto.response.employees.EmployeesResponse;
import myrestaurant.entity.Employees;
import myrestaurant.entity.MenuItem;

import java.util.List;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {
    private List<MenuItem> menuItems;
    private int currentPage;
    private int pageSize;

}
