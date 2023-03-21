package myrestaurant.dto.response;

import lombok.*;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesResponse {
    private Long id;
    private String email;
    private String token;
}
