package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.infrastructure.persistence.Filters;
import org.prosallo.model.Invitation;

import java.util.Optional;

@ApplicationScoped
public class DefaultInvitationRepository extends AbstractCrudRepository<Invitation, Long>
        implements InvitationRepository {

    @Override
    public boolean existsPendingByOrganizationIdAndEmail(Long organizationId, String email) {
        return countBy(
                Filters.eq(root -> root.get("organization").get("id"), organizationId),
                Filters.eq(root -> root.get("email"), email),
                Filters.eq(root -> root.get("accepted"), false)
        ) > 0;
    }

    @Override
    public Optional<Invitation> findByIdAndOrganizationId(Long id, Long organizationId) {
        return findOneBy(
                Filters.eq(root -> root.get("id"), id),
                Filters.eq(root -> root.get("organization").get("id"), organizationId)
        );
    }
}
