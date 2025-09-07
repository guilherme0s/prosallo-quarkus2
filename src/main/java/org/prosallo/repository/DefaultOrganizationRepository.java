package org.prosallo.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.model.Organization;

@ApplicationScoped
public class DefaultOrganizationRepository implements OrganizationRepository,
        PanacheRepositoryBase<Organization, Long> {

    @Override
    public Organization save(Organization organization) {
        if (organization.isNew()) {
            persist(organization);
            return organization;
        }

        return getEntityManager().merge(organization);
    }
}
