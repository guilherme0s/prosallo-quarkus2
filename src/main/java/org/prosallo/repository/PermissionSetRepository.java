package org.prosallo.repository;

import org.prosallo.infrastructure.persistence.CrudRepository;
import org.prosallo.model.PermissionSet;

import java.util.Optional;

public interface PermissionSetRepository extends CrudRepository<PermissionSet, Long> {

    Optional<PermissionSet> findByIdAndOrganizationId(Long id, Long organizationId);

    boolean existsByNameAndOrganizationId(String name, Long organizationId);
}
