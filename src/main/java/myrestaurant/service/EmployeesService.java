package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.dto.request.EmployeesRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.response.EmployeesResponse;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface EmployeesService {
    EmployeesResponse register(RegisterRequest registerRequest);
    EmployeesResponse authenticate(EmployeesRequest employeesRequest);
}
