package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.model.OrganizationMember;

import java.util.List;

@ApplicationScoped
public class DefaultOrganizationMemberRepository extends AbstractCrudRepository<OrganizationMember, Long>
        implements OrganizationMemberRepository {

    @Override
    public List<OrganizationMember> findAllByUserId(String userId) {
        return findBy(Filters.eq(root -> root.get("userId"), userId));
    }
}
