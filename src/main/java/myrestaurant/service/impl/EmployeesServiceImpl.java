package myrestaurant.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.employees.EmployeesRequest;
import myrestaurant.dto.request.RegisterRequest;
import myrestaurant.dto.request.employees.ResponseAcceptedEmployee;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.employees.EmployeesResponse;
import myrestaurant.entity.Employees;
import myrestaurant.enums.Role;
import myrestaurant.exceptions.ExistsElementException;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.exceptions.ValidationException;
import myrestaurant.jwt.JwtUtil;
import myrestaurant.repository.EmployeesRepository;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.service.EmployeesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * MyRestaurant
 * 2023
 * macbook_pro
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeesServiceImpl implements EmployeesService {
    private final EmployeesRepository employeesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;

    @PostConstruct
    public void saveAdmin() {
        if (!employeesRepository.existsByRole(Role.ADMIN)) {
            Employees employees = new Employees();
            employees.setEmail("admin2gmail.com");
            employees.setExperience(15);
            employees.setPassword(passwordEncoder.encode("admin123"));
            employees.setRole(Role.ADMIN);
            employees.setAccepted(true);
            employeesRepository.save(employees);
        }
    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        if (employeesRepository.findAll().size() == 15) {
            throw new RuntimeException("Sorry! no place for applications");
        }
        if (employeesRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ExistsElementException(
                    String.format("Employee with ROLE: %s is exists",
                            registerRequest.getRole()));
        }
        valid(registerRequest);
        Employees employees = Employees.builder()
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .experience(registerRequest.getExperience())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .accepted(false)
                .build();
        employeesRepository.save(employees);
        return new SimpleResponse(HttpStatus.OK, "Application has been successfully submitted for review...");
    }

    @Override
    public EmployeesResponse authenticate(EmployeesRequest employeesRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                employeesRequest.getEmail(),
                employeesRequest.getPassword()
        ));

        Employees employees = employeesRepository.findByEmail(employeesRequest.getEmail()).orElseThrow(() ->
                new NotFoundExceptionId(String.format("User %s is not found!", employeesRequest.getEmail())));

        return EmployeesResponse.builder()
                .id(employees.getId())
                .email(employeesRequest.getEmail())
                .token(jwtUtil.generateToken(employees))
                .build();
    }

    @Override
    public SimpleResponse updateEmployee(Long employeesId, RegisterRequest newEmployees) {
        Employees employees = employeesRepository.findById(employeesId)
                .orElseThrow(() ->
                        new NotFoundExceptionId(String.format("User id = %s is not found!", employeesId)));

        valid(newEmployees);
        employees.setLastName(newEmployees.getLastName());
        employees.setFirstName(newEmployees.getFirstName());
        employees.setExperience(newEmployees.getExperience());
        employees.setDateOfBirth(newEmployees.getDateOfBirth());
        employees.setPhoneNumber(newEmployees.getPhoneNumber());
        employees.setRole(newEmployees.getRole());
        employees.setEmail(newEmployees.getEmail());
        employees.setPassword(passwordEncoder.encode(newEmployees.getPassword()));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("UPDATED...").build();
    }

    private void valid(RegisterRequest newEmployees) {
        int waiterAge = LocalDate.now().minusYears(newEmployees.getDateOfBirth().getYear()).getYear();
        if (newEmployees.getRole().equals(Role.WAITER)) {
            if (waiterAge < 18 || waiterAge > 30) {
                throw new ValidationException("The age of the Whiter must not be less than 18 and more than 30");
            }
        }
        if (newEmployees.getRole().equals(Role.CHEF) && newEmployees.getExperience() < 2) {
            throw new ValidationException("Chef experience not less 2 years");
        } else if (newEmployees.getRole().equals(Role.WAITER) && newEmployees.getExperience() < 1) {
            throw new ValidationException("Waiter experience not less 1 years");
        }
        int age = LocalDate.now().minusYears(newEmployees.getDateOfBirth().getYear()).getYear();
        if (newEmployees.getRole().equals(Role.CHEF)) {
            if (age < 25 || age > 45) {
                throw new ValidationException("Cook's age should be less than 25 and more than 45");
            }
        }
    }

    @Override
    public EmployeesResponse acceptEmployee(ResponseAcceptedEmployee responseAcceptedEmployee) {
        Employees employees = employeesRepository.findById(responseAcceptedEmployee.getEmployeeId())
                .orElseThrow(() -> new NotFoundExceptionId(String.format("User id =  %s is not found!", responseAcceptedEmployee.getEmployeeId())));
        if (employees.isAccepted() == responseAcceptedEmployee.isAccept()) {
            employeesRepository.deleteById(responseAcceptedEmployee.getEmployeeId());
            System.out.println("Sorry we can't here heir you");
        }else {
            employees.setRestaurant(restaurantRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundExceptionId(String.format("User id =  %s is not found!",1L))));
        }
        return EmployeesResponse.builder()
                .id(employees.getId())
                .email(employees.getEmail())
                .token(jwtUtil.generateToken(employees))
                .build();
    }

    @Override
    public SimpleResponse deleteEmployeeBYId(Long employeeId) {
        employeesRepository.deleteById(employeeId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED...").build();
    }
}
