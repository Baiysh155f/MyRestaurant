package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.cheques.ChequeRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.checues.EmployeesDailyTotalResponse;
import myrestaurant.dto.response.checues.RestaurantDailyTotalChequeResponse;
import myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque;
import myrestaurant.entity.Cheque;
import myrestaurant.entity.Employees;
import myrestaurant.entity.MenuItem;
import myrestaurant.entity.Restaurant;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.ChequeRepository;
import myrestaurant.repository.EmployeesRepository;
import myrestaurant.repository.MenuItemRepository;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.service.ChequeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final EmployeesRepository employeesRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse save(Long employeeId, ChequeRequest chequeRequest) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundExceptionId("Employee id = " + employeeId + " mot found..."));
        List<MenuItem> allById = menuItemRepository.findAllById(chequeRequest.getMenuItemsId());
        Cheque cheque = new Cheque();
        cheque.setCreateAt(LocalDate.now());
        cheque.setEmployees(employees);
        BigDecimal average = new BigDecimal(0);
        for (MenuItem menuItem : allById) {
            menuItem.getCheques().add(cheque);
            average = average.add(menuItem.getPrice());
        }
        cheque.setPriceAverage(average);
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SAVED...").build();
    }

    @Override
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundExceptionId("Cheque id = " + chequeId + " mot found..."));
        List<MenuItem> allById = menuItemRepository.findAllById(chequeRequest.getMenuItemsId());
        cheque.setCreateAt(LocalDate.now());
        BigDecimal average = new BigDecimal(0);
        for (MenuItem menuItem : allById) {
            menuItem.getCheques().add(cheque);
            average = average.add(menuItem.getPrice());
        }
        cheque.setPriceAverage(average);
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public SimpleResponse deleteById(Long chequeId) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundExceptionId("Cheque id = " + chequeId + " mot found..."));
        List<MenuItem> menuItem = cheque.getMenuItem();
        for (MenuItem item : menuItem) {
            item.setCheques(null);
        }
        chequeRepository.delete(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Deleted...").build();
    }

    @Override
    public List<ChequeResponseGrandTotal> getAllByEmployeeId(Long employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundExceptionId("Employee id = " + employeeId + " mot found..."));

        List<ChequeResponseGrandTotal> cheques = new ArrayList<>();
        for (ChequeResponseGrandTotal chequeResponseGrandTotal : chequeRepository.getAllChequeByUserId(employeeId)) {
            chequeResponseGrandTotal.setWaiter(employees.getFirstName() + " " + employees.getLastName());
            BigDecimal total = chequeResponseGrandTotal.getAveragePrice().multiply(new BigDecimal(chequeResponseGrandTotal
                    .getService())).divide(new BigDecimal(100)).add(chequeResponseGrandTotal.getAveragePrice());
            chequeResponseGrandTotal.setGrandTotal(total);
            List<MenuItemsResponseForCheque> items = chequeRepository.getItems(chequeResponseGrandTotal.getId());
            chequeResponseGrandTotal.setItems(items);
            cheques.add(chequeResponseGrandTotal);
        }
        return cheques;
    }

    @Override
    public EmployeesDailyTotalResponse employeesDailyTotal(Long employeeId, LocalDate date) {
        Long sum = 0L;
        int service = 1;
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundExceptionId("Cheque id = " + employeeId + " mot found..."));
        for (Cheque cheque : employees.getCheque()) {
            if (cheque.getCreateAt().equals(date)) {
                for (MenuItem menuItem : cheque.getMenuItem()) {
                    sum = +menuItem.getPrice().longValue();
                    service = menuItem.getRestaurant().getService();
                }
            } else {
                throw new NotFoundExceptionId("Not found cheque date = " + date);
            }
        }
        BigDecimal allSum = new BigDecimal(sum);
        BigDecimal totalSum = allSum.multiply(new BigDecimal(service)).divide(new BigDecimal(100));
        return EmployeesDailyTotalResponse.builder()
                .employeeName(employees.getFirstName())
                .date(date)
                .allSum(totalSum)
                .build();
    }

    @Override
    public RestaurantDailyTotalChequeResponse restaurantDailyTotalCheque(Long restaurantId, LocalDate localDate) {
        Long cheques = 0L;
        int service = 1;
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundExceptionId("Restaurant id = " + restaurantId + " mot found..."));
        service = +restaurant.getService();
        for (MenuItem menuItem : restaurant.getMenuItem()) {
            for (Cheque cheque : menuItem.getCheques()) {
                if (cheque.getCreateAt().equals(localDate)) {
                    cheques = +cheque.getPriceAverage().longValue();
                } else {
                    throw new NotFoundExceptionId("Not found cheque date = " + localDate);
                }
            }
        }
        BigDecimal allSum = new BigDecimal(cheques);
        BigDecimal totalSum = allSum.multiply(new BigDecimal(service)).divide(new BigDecimal(100));
        return RestaurantDailyTotalChequeResponse.builder()
                .resName(restaurant.getName())
                .date(localDate)
                .totalSumDaily(totalSum)
                .build();
    }

}
