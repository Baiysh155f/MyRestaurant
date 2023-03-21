package myrestaurant.api;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.cheques.ChequeRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.checues.ChequeResponseTotal;
import myrestaurant.service.ChequeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{chequeId}")
    public SimpleResponse update(@PathVariable Long chequeId,
                                 @RequestBody ChequeRequest chequeRequest) {
        return chequeService.update(chequeId, chequeRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse delete(@PathVariable Long chequeId) {
        return chequeService.deleteById(chequeId);
    }


}
