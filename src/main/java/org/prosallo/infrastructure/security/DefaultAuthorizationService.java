package org.prosallo.infrastructure.security;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.infrastructure.exception.ForbiddenException;
import org.prosallo.model.Organization;
import org.prosallo.repository.OrganizationRepository;

@ApplicationScoped
public class DefaultAuthorizationService implements AuthorizationService {

    private final OrganizationRepository organizationRepository;

    public DefaultAuthorizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    @CacheResult(cacheName = "isOrganizationOwner")
    public boolean isOrganizationOwner(String userId, Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(OrganizationNotFoundException::new);

        return organization.getOwnerId().equals(userId);
    }

    @Override
    public void requireOrganizationOwnership(String userId, Long organizationId) {
        if (!isOrganizationOwner(userId, organizationId)) {
            throw new ForbiddenException("You do not have permission to perform this action on the organization");
        }
    }
}
