package myrestaurant.service;

import myrestaurant.dto.request.cheques.ChequeRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.checues.EmployeesDailyTotalResponse;
import myrestaurant.dto.response.checues.RestaurantDailyTotalChequeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface ChequeService {
    SimpleResponse save(Long  employeeId,ChequeRequest chequeRequest);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SimpleResponse deleteById(Long chequeId);

    List<ChequeResponseGrandTotal> getAllByEmployeeId(Long employeeId);

    EmployeesDailyTotalResponse employeesDailyTotal(Long employeeId, LocalDate date);

    RestaurantDailyTotalChequeResponse restaurantDailyTotalCheque(Long restaurantId, LocalDate localDate);
}
