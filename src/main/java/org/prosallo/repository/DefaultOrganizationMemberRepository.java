package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.infrastructure.persistence.Page;
import org.prosallo.infrastructure.persistence.Pageable;
import org.prosallo.model.OrganizationMember;

import java.util.List;

@ApplicationScoped
public class DefaultOrganizationMemberRepository extends AbstractCrudRepository<OrganizationMember, Long>
        implements OrganizationMemberRepository {

    @Override
    public List<OrganizationMember> findAllByUserId(String userId) {
        return findBy(Filters.eq(root -> root.get("userId"), userId));
    }

    @Override
    public boolean existsByUserIdAndOrganizationId(String userId, Long organizationId) {
        return countBy(
                Filters.eq(root -> root.get("userId"), userId),
                Filters.eq(root -> root.get("organization").get("id"), organizationId)
        ) > 0;
    }

    @Override
    public Page<OrganizationMember> findAllByOrganizationId(Long organizationId, Pageable pageable) {
        return findAllBy(pageable, Filters.eq(root -> root.get("organization").get("id"), organizationId));
    }
}
