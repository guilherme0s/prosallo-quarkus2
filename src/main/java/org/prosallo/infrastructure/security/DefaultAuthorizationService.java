package org.prosallo.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.infrastructure.exception.ForbiddenException;
import org.prosallo.model.Organization;
import org.prosallo.repository.OrganizationMemberRepository;
import org.prosallo.repository.OrganizationRepository;

@ApplicationScoped
public class DefaultAuthorizationService implements AuthorizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    public DefaultAuthorizationService(OrganizationRepository organizationRepository,
            OrganizationMemberRepository organizationMemberRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMemberRepository = organizationMemberRepository;
    }

    @Override
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

    @Override
    public void requireMembership(String userId, Long organizationId) {
        if (!organizationMemberRepository.existsByUserIdAndOrganizationId(userId, organizationId)) {
            throw new ForbiddenException("You are not a member of this organization");
        }
    }
}
