package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.model.PermissionSet;

import java.util.Optional;

@ApplicationScoped
public class DefaultPermissionSetRepository extends AbstractCrudRepository<PermissionSet, Long>
        implements PermissionSetRepository {

    @Override
    public Optional<PermissionSet> findByIdAndOrganizationId(Long id, Long organizationId) {
        return findOneBy(
                Filters.eq(root -> root.get("id"), id),
                Filters.eq(root -> root.get("organization").get("id"), organizationId)
        );
    }

    @Override
    public boolean existsByNameAndOrganizationId(String name, Long organizationId) {
        return countBy(
                Filters.eq(root -> root.get("name"), name),
                Filters.eq(root -> root.get("organization").get("id"), organizationId)
        ) > 0;
    }
}
