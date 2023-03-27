package myrestaurant.dto.request.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAcceptedEmployee {
    private Long employeeId;
    private Boolean accept;
}
