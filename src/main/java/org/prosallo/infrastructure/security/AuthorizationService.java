package org.prosallo.infrastructure.security;

public interface AuthorizationService {

    boolean isOrganizationOwner(String userId, Long organizationId);

    void requireOrganizationOwnership(String userId, Long organizationId);
}
