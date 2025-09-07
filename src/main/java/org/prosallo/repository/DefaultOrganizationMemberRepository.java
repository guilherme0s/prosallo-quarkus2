package org.prosallo.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.model.OrganizationMember;

@ApplicationScoped
public class DefaultOrganizationMemberRepository implements OrganizationMemberRepository,
        PanacheRepositoryBase<OrganizationMember, Long> {

    @Override
    public OrganizationMember save(OrganizationMember organizationMember) {
        if (organizationMember.isNew()) {
            persist(organizationMember);
            return organizationMember;
        }
        return getEntityManager().merge(organizationMember);
    }
}
