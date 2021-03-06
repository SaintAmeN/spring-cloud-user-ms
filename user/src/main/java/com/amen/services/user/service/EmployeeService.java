package com.amen.services.user.service;

import com.amen.services.user.model.mapper.EmployeeMapperDecorator;
import com.amen.services.user.model.userservice.EmployeeDto;
import com.amen.services.user.exception.usageerrors.NonExistentEntity;
import com.amen.services.user.model.domain.Employee;
import com.amen.services.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmployeeMapperDecorator employeeMapper;

    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee modifyEmployee(EmployeeDto dto, Long id) {
        dto.validateEmployeeEditForm();
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();

            employeeMapper.updateEmployee(employee, dto);

            return employeeRepository.save(employee);
        }
        throw new NonExistentEntity();
    }

    public String getEncryptedPassword(String username){
        return employeeRepository.getEmployeeByUsername(username).getPassword();
    }
}
