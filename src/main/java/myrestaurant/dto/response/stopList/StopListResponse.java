package myrestaurant.dto.response.stopList;

import lombok.*;

import java.time.LocalDate;

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
public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;
}
