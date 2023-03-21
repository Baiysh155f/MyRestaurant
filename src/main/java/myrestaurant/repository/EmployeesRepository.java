package myrestaurant.repository;

import myrestaurant.entity.Employees;
import myrestaurant.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    Optional<Employees> findByEmail(String email);
    Optional<Employees> findByRole(Role role);
    Boolean existsByRole(Role role);
    Boolean existsByEmail(String email);

}