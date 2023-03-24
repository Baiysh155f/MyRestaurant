package myrestaurant.repository;

import myrestaurant.dto.response.employees.EmployeesResponseAll;
import myrestaurant.entity.Employees;
import myrestaurant.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    Optional<Employees> findByEmail(String email);
    Optional<Employees> findByRole(Role role);
    Boolean existsByRole(Role role);
    Boolean existsByEmail(String email);
    @Override
    Page<Employees> findAll(Pageable pageable);
    @Query("select new myrestaurant.dto.response.employees.EmployeesResponseAll(e.id,e.firstName,e.lastName,e.dateOfBirth,e.email,e.password,e.phoneNumber,e.role,e.experience,e.accepted) from Employees e")
    List<EmployeesResponseAll> getAll();

}