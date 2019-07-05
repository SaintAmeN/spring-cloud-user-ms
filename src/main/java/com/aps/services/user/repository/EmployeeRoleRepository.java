package com.aps.services.user.repository;

import com.aps.services.user.model.domain.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

    Optional<EmployeeRole> findByName(String name);
}
