package com.aps.services.user.repository;


import com.aps.services.user.model.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    Employee getEmployeeByUsername(String username);
}
