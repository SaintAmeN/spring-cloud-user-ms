package com.aps.services.user.controller;

import com.aps.services.model.dto.common.AbstractResponse;
import com.aps.services.model.dto.userservice.EmployeeDto;
import com.aps.services.model.dto.userservice.responses.AuthenticationResponse;
import com.aps.services.user.model.domain.AccountRole;
import com.aps.services.user.model.domain.Employee;
import com.aps.services.user.model.domain.IBaseEntity;
import com.aps.services.user.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@RestController
@RequestMapping("/employee/")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/update")
    public AbstractResponse update(@RequestBody EmployeeDto updateData) {
        Employee edited = employeeService.modifyEmployee(updateData, updateData.getId());

        return IBaseEntity.createUpdateResponse(edited);
    }

    @PostMapping("/successlogin")
    public ResponseEntity<AuthenticationResponse> index(ModelMap model, Principal principal) {
        Optional<Employee> employee = employeeService.findByUsername(principal.getName());
        if(employee.isPresent()) {
            Employee u = employee.get();
            return ResponseEntity.ok(new AuthenticationResponse(
                    String.valueOf(u.getEmail()),
                    String.valueOf(u.getPassword()),
                    u.getRoles().stream().map(AccountRole::getName).collect(Collectors.toList())));
        }
        return ResponseEntity.notFound().build();
    }
}
