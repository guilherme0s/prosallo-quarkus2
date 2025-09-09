package org.prosallo.infrastructure.security;

/**
 * Represents the context of the currently authenticated principal for a request.
 * <p>
 * This is a request-scoped bean that provides a convenient way to access the
 * principal's identity and perform authorization checks.
 */
public interface SecurityContext {

    /**
     * Gets the unique identifier of the authenticated principal (e.g., user).
     *
     * @return The principal's unique ID (typically the subject from the JWT).
     */
    String getAuthenticatedUserId();

    /**
     * Verifies that the current principal is the owner of the specified organization.
     *
     * @param organizationId The ID of the organization to check ownership against.
     * @throws org.prosallo.infrastructure.exception.ForbiddenException if the principal is not the owner.
     */
    void requireOrganizationOwnership(Long organizationId);

    void requireMembership(Long organizationId);
}
