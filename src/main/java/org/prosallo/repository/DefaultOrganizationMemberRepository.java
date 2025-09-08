package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.model.OrganizationMember;

@ApplicationScoped
public class DefaultOrganizationMemberRepository extends AbstractCrudRepository<OrganizationMember, Long>
        implements OrganizationMemberRepository {

    @Override
    public boolean existsByUserIdAndOrganizationId(String userId, Long organizationId) {
        return countBy(
                Filters.eq(root -> root.get("userId"), userId),
                Filters.eq(root -> root.get("organization").get("id"), organizationId)
        ) > 0;
    }
}
