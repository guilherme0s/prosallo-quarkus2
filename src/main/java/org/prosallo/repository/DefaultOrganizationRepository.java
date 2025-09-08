package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.model.Organization;

@ApplicationScoped
public class DefaultOrganizationRepository extends AbstractCrudRepository<Organization, Long>
        implements OrganizationRepository {

    @Override
    public boolean existsByOwnerId(String ownerId) {
        return countBy(Filters.eq(root -> root.get("ownerId"), ownerId)) > 0;
    }
}
