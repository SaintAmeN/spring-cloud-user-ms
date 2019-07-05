package com.aps.services.user.model.domain;

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
public class AccountRole implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public AccountRole(String name) {
        this.name = name;
    }
}
