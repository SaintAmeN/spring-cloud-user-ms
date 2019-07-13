package com.aps.services.user.controller;

import com.aps.services.model.dto.common.AbstractResponse;
import com.aps.services.model.dto.userservice.EmployeeDto;
import com.aps.services.user.model.domain.Employee;
import com.aps.services.user.model.domain.IBaseEntity;
import com.aps.services.user.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@RestController
@RequestMapping("/employee/")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/update")
    public AbstractResponse update(@RequestBody EmployeeDto updateData) {
        Employee edited = employeeService.modifyEmployee(updateData, updateData.getId());

        return IBaseEntity.createUpdateResponse(edited);
    }
}
