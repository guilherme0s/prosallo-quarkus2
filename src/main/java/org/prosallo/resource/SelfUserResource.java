package org.prosallo.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.OrganizationResponse;
import org.prosallo.infrastructure.security.SecurityContext;
import org.prosallo.service.OrganizationService;

import java.util.List;

@Path("/api/v1/users/self")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SelfUserResource {

    private final SecurityContext securityContext;
    private final OrganizationService organizationService;

    public SelfUserResource(SecurityContext securityContext, OrganizationService organizationService) {
        this.securityContext = securityContext;
        this.organizationService = organizationService;
    }

    @GET
    @Path("/organizations")
    public Response listOrganizations() {
        String userId = securityContext.getAuthenticatedUserId();
        List<OrganizationResponse> organizations = organizationService.listOrganizationsForUser(userId);
        return Response.ok(organizations).build();
    }
}
