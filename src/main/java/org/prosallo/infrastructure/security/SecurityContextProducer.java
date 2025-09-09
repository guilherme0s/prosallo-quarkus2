package org.prosallo.infrastructure.security;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;

/**
 * A CDI producer for creating a request-scoped {@link SecurityContext}.
 * <p>
 * This class centralizes the logic for identifying the currently authenticated principal from the
 * security context and making it available for dependency injection.
 */
@ApplicationScoped
public class SecurityContextProducer {

    /**
     * Produces a request-scoped {@link SecurityContext} for the current authenticated principal.
     *
     * @param identity The current security identity provided by Quarkus.
     * @param authorizationService The service used to perform authorization checks.
     * @return A {@link SecurityContext} instance representing the current principal.
     */
    @Produces
    @RequestScoped
    public SecurityContext produceActor(SecurityIdentity identity, AuthorizationService authorizationService) {
        if (identity.isAnonymous()) {
            throw new IllegalStateException("Authentication is required to access this resource");
        }

        var principal = identity.getPrincipal(JWTCallerPrincipal.class);
        if (principal == null || principal.getSubject() == null) {
            throw new IllegalStateException("No JWT principal found");
        }

        String principalId = principal.getSubject();
        return new DefaultSecurityContext(principalId, authorizationService);
    }

    /**
     * The concrete request-scoped implementation of the {@link SecurityContext} interface.
     */
    private record DefaultSecurityContext(String userId, AuthorizationService authorizationService)
            implements SecurityContext {

        @Override
        public String getAuthenticatedUserId() {
            return userId;
        }

        @Override
        public void requireOrganizationOwnership(Long organizationId) {
            authorizationService.requireOrganizationOwnership(userId, organizationId);
        }

        @Override
        public void requireMembership(Long organizationId) {
            authorizationService.requireMembership(userId, organizationId);
        }
    }
}
