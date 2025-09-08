package org.prosallo.repository;

import org.prosallo.model.Organization;

import java.util.Optional;

public interface OrganizationRepository {

    Organization save(Organization organization);

    Optional<Organization> findById(Long id);

    boolean existsByOwnerId(String ownerId);
}
