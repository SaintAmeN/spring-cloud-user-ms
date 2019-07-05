package com.aps.services.user.model.dto;

import com.aps.services.user.exception.usageerrors.EmptyFormField;
import com.aps.services.user.model.domain.EmployeeRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private EmployeeRole role;


    public void validateEmployeeEditForm(){
        if (getEmail().isEmpty()){
            throw new EmptyFormField("Email");
        }
    }
}
