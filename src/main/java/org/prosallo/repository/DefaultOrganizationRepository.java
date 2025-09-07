package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.prosallo.model.Organization;

import java.util.Optional;

@ApplicationScoped
public class DefaultOrganizationRepository implements OrganizationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Organization save(Organization organization) {
        if (organization.isNew()) {
            em.persist(organization);
            return organization;
        }

        return em.merge(organization);
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return Optional.ofNullable(em.find(Organization.class, id));
    }
}
