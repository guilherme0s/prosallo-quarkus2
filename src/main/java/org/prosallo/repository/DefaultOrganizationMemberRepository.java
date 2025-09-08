package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.prosallo.model.OrganizationMember;

import java.util.Optional;

@ApplicationScoped
public class DefaultOrganizationMemberRepository implements OrganizationMemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public OrganizationMember save(OrganizationMember organizationMember) {
        if (organizationMember.isNew()) {
            em.persist(organizationMember);
            return organizationMember;
        }
        return em.merge(organizationMember);
    }

    @Override
    public Optional<OrganizationMember> findById(Long id) {
        return Optional.ofNullable(em.find(OrganizationMember.class, id));
    }
}
