package myrestaurant.dto.request;

import lombok.*;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeesRequest {
    private String email;
    private String password;
}
