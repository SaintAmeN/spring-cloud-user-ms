package com.aps.services.user.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRole implements IBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public EmployeeRole(String name) {
        this.name = name;
    }
}
