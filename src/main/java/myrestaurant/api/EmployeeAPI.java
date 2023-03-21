package myrestaurant.api;

import myrestaurant.dto.request.employees.EmployeesRequest;
import myrestaurant.dto.request.RegisterRequest;
import myrestaurant.dto.request.employees.ResponseAcceptedEmployee;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.employees.EmployeesResponse;
import myrestaurant.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@RestController
@RequestMapping("/api/employees")
public class EmployeeAPI {
    private final EmployeesService employeesService;
    @Autowired
    public EmployeeAPI(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping("/register")
    public SimpleResponse register(@RequestBody RegisterRequest registerRequest) {
       return employeesService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public EmployeesResponse authenticate(@RequestBody EmployeesRequest employeesRequest) {
        return employeesService.authenticate(employeesRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @PutMapping("/{employeeId}")
    public SimpleResponse updateEmployee(@PathVariable Long employeeId ,
                                         @RequestBody RegisterRequest registerRequest){
        return employeesService.updateEmployee(employeeId,registerRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/accept")
    public EmployeesResponse acceptEmployee(@RequestBody ResponseAcceptedEmployee employee){
        return employeesService.acceptEmployee(employee);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public SimpleResponse deleteEmployee(@PathVariable Long employeeId){
        return employeesService.deleteEmployeeBYId(employeeId);
    }
}
