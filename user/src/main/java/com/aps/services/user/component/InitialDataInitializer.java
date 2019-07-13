package com.aps.services.user.component;


import com.aps.services.user.model.domain.Employee;
import com.aps.services.user.model.domain.AccountRole;
import com.aps.services.user.repository.EmployeeRepository;
import com.aps.services.user.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataInitializer implements
        ApplicationListener<ContextRefreshedEvent> {

    private EmployeeRepository employeeRepository;
    private EmployeeRoleRepository employeeRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataInitializer(EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        addRole("ROLE_ADMIN");
        addRole("ROLE_USER");
        addRole("ROLE_SENSOR");

        addEmployee("admin", "admin", "p.reclaw@apsystems.tech", "ROLE_ADMIN", "ROLE_USER");
        addEmployee("user", "user", "p.reclaw@apsystems.tech", "ROLE_USER");
    }

    @Transactional
    public void addRole(String name) {
        Optional<AccountRole> roleOpt = employeeRoleRepository.findByName(name);
        if (!roleOpt.isPresent()) {
            employeeRoleRepository.save(new AccountRole(name));
        }
    }

    @Transactional
    public void addEmployee(String username, String password, String email, String... roles) {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(username);
        if (!employeeOptional.isPresent()) {
            List<AccountRole> rolesList = new ArrayList<>();
            for (String role : roles) {
                Optional<AccountRole> employeeRoleOptional = employeeRoleRepository.findByName(role);
                employeeRoleOptional.ifPresent(rolesList::add);
            }

            employeeRepository.save(new Employee(
                    null, // id
                    null,
                    null,
                    username,                                      // username
                    passwordEncoder.encode(password),              // password
                    null,              // email
                    new HashSet<>(rolesList),
                    null));
        }
    }
}
