package com.aps.services.user.model.mapper;

import com.aps.services.user.model.domain.Employee;
import com.aps.services.user.model.dto.EmployeeDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(EmployeeMapperDecorator.class)
public interface EmployeeMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(ignore = true, target = "role"),
            @Mapping(ignore = true, target = "activationId"),
    })
    Employee dtoToEmployee(EmployeeDto employeeModifyDto);

    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "username", target = "username"),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    EmployeeDto employeeToDto(Employee employee);
}