package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.cheques.ChequeRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.checues.EmployeesDailyTotalResponse;
import myrestaurant.dto.response.checues.RestaurantDailyTotalChequeResponse;
import myrestaurant.service.ChequeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeAPI {
    private final ChequeService chequeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{employeeId}")
    public List<ChequeResponseGrandTotal> getChequeTotal(@PathVariable Long employeeId) {
        return chequeService.getAllByEmployeeId(employeeId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{employeeId}")
    public SimpleResponse save(@PathVariable Long employeeId,
                               @RequestBody ChequeRequest chequeRequest) {
        return chequeService.save(employeeId, chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{chequeId}")
    public SimpleResponse update(@PathVariable Long chequeId,
                                 @RequestBody ChequeRequest chequeRequest) {
        return chequeService.update(chequeId, chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse delete(@PathVariable Long chequeId) {
        return chequeService.deleteById(chequeId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/dailyTotal/{employeeId}")
    public EmployeesDailyTotalResponse employeesDailyTotal(@PathVariable Long employeeId,
                                                           @RequestParam LocalDate localDate) {
        return chequeService.employeesDailyTotal(employeeId, localDate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/restaurantChequeTotal/{restaurantId}")
    public RestaurantDailyTotalChequeResponse restaurantDailyTotalCheque(@PathVariable Long restaurantId,
                                                                         @RequestParam LocalDate localDate) {
        return chequeService.restaurantDailyTotalCheque(restaurantId, localDate);
    }
}
