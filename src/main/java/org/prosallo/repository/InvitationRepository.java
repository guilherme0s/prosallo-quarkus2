package org.prosallo.repository;

import org.prosallo.infrastructure.persistence.CrudRepository;
import org.prosallo.model.Invitation;

import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {

    boolean existsPendingByOrganizationIdAndEmail(Long organizationId, String email);

    Optional<Invitation> findByIdAndOrganizationId(Long id, Long organizationId);
}
