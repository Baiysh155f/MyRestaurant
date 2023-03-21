package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.cheques.ChequeRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.checues.ChequeResponseGrandTotal;
import myrestaurant.dto.response.menuItem.MenuItemsResponseForCheque;
import myrestaurant.entity.Cheque;
import myrestaurant.entity.Employees;
import myrestaurant.entity.MenuItem;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.ChequeRepository;
import myrestaurant.repository.EmployeesRepository;
import myrestaurant.repository.MenuItemRepository;
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
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    @Override
    public List<ChequeResponseGrandTotal> getAllByEmployeeId(Long employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundExceptionId("Employee id = " + employeeId + " mot found..."));

        List<ChequeResponseGrandTotal> cheques = new ArrayList<>();
        for (ChequeResponseGrandTotal chequeResponseGrandTotal : chequeRepository.getAllChequeByUserId(employeeId)) {
            chequeResponseGrandTotal.setWaiter(employees.getFirstName()+" "+employees.getLastName());
            BigDecimal total = chequeResponseGrandTotal.getAveragePrice().multiply(new BigDecimal(chequeResponseGrandTotal
                    .getService())).divide(new BigDecimal(100)).add(chequeResponseGrandTotal.getAveragePrice());
            chequeResponseGrandTotal.setGrandTotal(total);
            List<MenuItemsResponseForCheque> items = chequeRepository.getItems(chequeResponseGrandTotal.getId());
            chequeResponseGrandTotal.setItems(items);
            cheques.add(chequeResponseGrandTotal);
        }
        return cheques;
    }

}
