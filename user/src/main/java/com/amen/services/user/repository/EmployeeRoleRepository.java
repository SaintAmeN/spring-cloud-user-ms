package com.amen.services.user.repository;

import com.amen.services.user.model.domain.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
@Repository
public interface EmployeeRoleRepository extends JpaRepository<AccountRole, Long> {

    Optional<AccountRole> findByName(String name);
}
