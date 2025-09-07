package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.prosallo.model.PermissionSet;

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
}
