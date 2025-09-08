package org.prosallo.repository;

import org.prosallo.model.Invitation;

import java.util.Optional;

public interface InvitationRepository {

    Invitation save(Invitation invitation);

    Optional<Invitation> findByToken(String token);

    boolean existsPendingByOrganizationIdAndEmail(Long organizationId, String email);

    Optional<Invitation> findByIdAndOrganizationId(Long id, Long organizationId);

    void delete(Invitation invitation);
}
