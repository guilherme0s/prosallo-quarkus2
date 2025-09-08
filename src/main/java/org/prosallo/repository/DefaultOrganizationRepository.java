package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.model.Organization;

@ApplicationScoped
public class DefaultOrganizationRepository extends AbstractCrudRepository<Organization, Long>
        implements OrganizationRepository {
}
