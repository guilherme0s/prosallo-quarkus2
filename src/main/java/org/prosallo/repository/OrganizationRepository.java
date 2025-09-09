package org.prosallo.repository;

import org.prosallo.infrastructure.persistence.CrudRepository;
import org.prosallo.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    boolean existsByOwnerId(String ownerId);
}
