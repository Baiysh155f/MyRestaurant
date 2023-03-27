package myrestaurant.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.employees.EmployeesRequest;
import myrestaurant.dto.request.RegisterRequest;
import myrestaurant.dto.request.employees.ResponseAcceptedEmployee;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.employees.EmployeesResponse;
import myrestaurant.dto.response.employees.EmployeesResponseAll;
import myrestaurant.entity.Cheque;
import myrestaurant.entity.Employees;
import myrestaurant.enums.Role;
import myrestaurant.exceptions.ExistsElementException;
import myrestaurant.exceptions.NoVacancyException;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.exceptions.ValidationException;
import myrestaurant.jwt.JwtUtil;
import myrestaurant.repository.EmployeesRepository;
import myrestaurant.repository.RestaurantRepository;
import myrestaurant.repository.SubCategoryRepository;
import myrestaurant.service.EmployeesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    private final SubCategoryRepository subCategoryRepository;

    @PostConstruct
    public void saveAdmin() {
        if (!employeesRepository.existsByRole(Role.ADMIN)) {
            Employees employees = new Employees();
            employees.setFirstName("Baiysh");
            employees.setLastName("Orozaliev");
            employees.setPhoneNumber("+996223445566");
            employees.setEmail("admin2gmail.com");
            employees.setExperience(15);
            employees.setPassword(passwordEncoder.encode("admin123"));
            employees.setRole(Role.ADMIN);
            employees.setAccepted(true);
            employeesRepository.save(employees);
        }
    }

    @Override
    public List<EmployeesResponseAll> getAll() {
        return employeesRepository.getAll();
    }

    @Override
    public EmployeesResponseAll findById(Long employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundExceptionId(String.format("User id = %s is not found!", employeeId)));
        return EmployeesResponseAll.builder()
                .id(employees.getId())
                .accepted(employees.isAccepted())
                .dateOfBirth(employees.getDateOfBirth())
                .email(employees.getEmail())
                .experience(employees.getExperience())
                .firstName(employees.getFirstName())
                .lastName(employees.getLastName())
                .password(passwordEncoder.encode(employees.getPassword()))
                .phoneNumber(employees.getPhoneNumber())
                .role(employees.getRole())
                .build();
    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        List<Employees> all = employeesRepository.findAll();
        for (Employees employees : all) {
            if (employees.getId() == 15) {
                throw new NoVacancyException("Sorry! no place for applications");
            }

        }

        if (employeesRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ExistsElementException(
                    String.format("Employee with email : %s is exists",
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
    public SimpleResponse acceptEmployee(ResponseAcceptedEmployee responseAcceptedEmployee) {
        Employees employees = employeesRepository.findById(responseAcceptedEmployee.getEmployeeId())
                .orElseThrow(() -> new NotFoundExceptionId(String.format("User id =  %s is not found!", responseAcceptedEmployee.getEmployeeId())));
        if (employees.isAccepted() == responseAcceptedEmployee.getAccept()) {
            employeesRepository.deleteById(responseAcceptedEmployee.getEmployeeId());
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Sorry we can't here heir you")
                    .build();
        } else {
            employees.setRestaurant(restaurantRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundExceptionId(String.format("Restaurant id =  %s is not found!", 1L))));
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(employees.getUsername() + " You have been successfully hired !)")
                    .build();
        }

    }

    @Override
    public SimpleResponse deleteEmployeeBYId(Long employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundExceptionId(String.format("User id = %s is not found!", employeeId)));
        for (Cheque cheque : employees.getCheque()) {
            cheque.setEmployees(null);
        }
        employeesRepository.delete(employees);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("DELETED...").build();
    }
}
