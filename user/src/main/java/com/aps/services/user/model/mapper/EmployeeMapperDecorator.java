package com.aps.services.user.model.mapper;

import com.aps.services.model.dto.userservice.EmployeeDto;
import com.aps.services.user.model.domain.Employee;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor
@Component
public abstract class EmployeeMapperDecorator implements EmployeeMapper {
    @Autowired
    private EmployeeMapper delegate;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    EmployeeMapperDecorator(EmployeeMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public Employee dtoToEmployee(EmployeeDto dto) {
        Employee employee = delegate.dtoToEmployee(dto);

        if (employee.getName().trim().isEmpty()) employee.setName(null);
        if (employee.getSurname().trim().isEmpty()) employee.setSurname(null);
        // todo: to samo z pozostałymi polami

        employee.setPassword(encoder.encode(dto.getPassword()));                // zmiana hasła

        return employee;
    }

    /**
     * Metoda wykorzystywana żeby aktualizować obiekt EMPLOYEE w bazie. Nadpisuje orginalne dane użytkownika.
     *
     * @param updated    - aktualizowany employee
     * @param updateData - dane aktualizujące (dto z kontrolera)
     */
    public void updateEmployee(Employee updated, EmployeeDto updateData) {
        log.debug("Updating employee from: " + updated);
        Employee updateInstance = dtoToEmployee(updateData);

        updated.setEmail(updateInstance.getEmail());
        updated.setName(updateInstance.getName());
        updated.setSurname(updateInstance.getSurname());
        updated.setPassword(updateInstance.getPassword());

        log.debug("Final look at the employee: " + updated + ". Modification data: " + updateData);
    }
}
