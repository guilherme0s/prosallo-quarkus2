package org.prosallo.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.OrganizationRequest;
import org.prosallo.data.OrganizationResponse;
import org.prosallo.infrastructure.security.SecurityContext;
import org.prosallo.service.OrganizationService;

@Path("/api/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    private final OrganizationService organizationService;
    private final SecurityContext securityContext;

    public OrganizationResource(OrganizationService organizationService, SecurityContext securityContext) {
        this.organizationService = organizationService;
        this.securityContext = securityContext;
    }

    @POST
    public Response create(@Valid OrganizationRequest request) {
        String userId = securityContext.getAuthenticatedUserId();
        OrganizationResponse response = organizationService.createOrganization(userId, request.name());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
