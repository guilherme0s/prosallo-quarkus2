package org.prosallo.repository;

import org.prosallo.model.PermissionSet;

import java.util.Optional;

public interface PermissionSetRepository {

    PermissionSet save(PermissionSet permissionSet);

    Optional<PermissionSet> findById(Long id);

    Optional<PermissionSet> findByNameAndOrganizationId(String name, Long organizationId);

    boolean existsByNameAndOrganizationId(String name, Long organizationId);
}
