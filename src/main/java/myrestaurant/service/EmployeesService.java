package myrestaurant.service;

import myrestaurant.dto.request.employees.EmployeesRequest;
import myrestaurant.dto.request.RegisterRequest;
import myrestaurant.dto.request.employees.ResponseAcceptedEmployee;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.employees.EmployeesResponse;


/**
 * Restorant
 * 2023
 * macbook_pro
 **/

public interface EmployeesService {
    SimpleResponse register(RegisterRequest registerRequest);

    EmployeesResponse authenticate(EmployeesRequest employeesRequest);

    SimpleResponse updateEmployee(Long employeesId, RegisterRequest newEmployees);

    EmployeesResponse acceptEmployee(ResponseAcceptedEmployee responseAcceptedEmployee);
    SimpleResponse deleteEmployeeBYId(Long employeeId);

}
