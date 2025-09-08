package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.prosallo.model.PermissionSet;

import java.util.Optional;

@ApplicationScoped
public class DefaultPermissionSetRepository implements PermissionSetRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PermissionSet save(PermissionSet permissionSet) {
        if (permissionSet.isNew()) {
            em.persist(permissionSet);
            return permissionSet;
        }
        return em.merge(permissionSet);
    }

    @Override
    public Optional<PermissionSet> findById(Long id) {
        return Optional.ofNullable(em.find(PermissionSet.class, id));
    }

    @Override
    public Optional<PermissionSet> findByNameAndOrganizationId(String name, Long organizationId) {
        try {
            return Optional.of(em.createQuery("SELECT ps FROM PermissionSet ps WHERE ps.name = :name AND ps.organization.id = :organizationId", PermissionSet.class)
                    .setParameter("name", name)
                    .setParameter("organizationId", organizationId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByNameAndOrganizationId(String name, Long organizationId) {
        TypedQuery<Long> query = em.createQuery("""
                        SELECT COUNT (ps)
                        FROM PermissionSet ps
                        WHERE ps.name = :name
                          AND ps.organization.id = :organizationId
                        """,
                Long.class);

        query.setParameter("name", name);
        query.setParameter("organizationId", organizationId);

        return query.getSingleResult() > 0;
    }
}
