package com.aps.services.user.service;

import com.aps.services.user.model.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> EmployeeOptional = employeeService.findByUsername(username);
        if (EmployeeOptional.isPresent()) {
            Employee employee = EmployeeOptional.get();

            // todo: validate ip call

            return User
                    .withUsername(employee.getUsername())
                    .password(employee.getPassword())
                    .accountLocked(employee.getActivationId() != null)
                    .disabled(employee.getActivationId() != null)
                    .roles(extractRoles(employee))
                    .build();
        }
        throw new UsernameNotFoundException("User not found by name: " + username);
    }

    private String[] extractRoles(Employee userAccount) {
        List<String> roles = userAccount.getRoles()
                .stream()
                .map(role -> role.getName().replace("ROLE_", ""))
                .collect(Collectors.toList());

        String[] rolesArray = new String[roles.size()];
        rolesArray = roles.toArray(rolesArray);

        return rolesArray;
    }
}
