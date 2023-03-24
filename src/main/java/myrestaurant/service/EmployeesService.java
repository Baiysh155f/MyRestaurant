package myrestaurant.service;

import myrestaurant.dto.request.employees.EmployeesRequest;
import myrestaurant.dto.request.RegisterRequest;
import myrestaurant.dto.request.employees.ResponseAcceptedEmployee;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.employees.EmployeesResponse;
import myrestaurant.dto.response.employees.EmployeesResponseAll;

import java.util.List;


/**
 * Restorant
 * 2023
 * macbook_pro
 **/

public interface EmployeesService {
    SimpleResponse register(RegisterRequest registerRequest);

    EmployeesResponse authenticate(EmployeesRequest employeesRequest);

    SimpleResponse updateEmployee(Long employeesId, RegisterRequest newEmployees);

    SimpleResponse acceptEmployee(ResponseAcceptedEmployee responseAcceptedEmployee);

    SimpleResponse deleteEmployeeBYId(Long employeeId);

    List<EmployeesResponseAll> getAll();

    EmployeesResponseAll findById(Long employeeId);
}
