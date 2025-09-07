package org.prosallo.resource;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.OrganizationRequest;
import org.prosallo.data.OrganizationResponse;
import org.prosallo.service.OrganizationService;

@Path("/api/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    private final OrganizationService organizationService;
    private final SecurityIdentity securityIdentity;

    public OrganizationResource(OrganizationService organizationService, SecurityIdentity securityIdentity) {
        this.organizationService = organizationService;
        this.securityIdentity = securityIdentity;
    }

    @POST
    public Response create(@Valid OrganizationRequest request) {
        JWTCallerPrincipal principal = securityIdentity.getPrincipal(JWTCallerPrincipal.class);
        OrganizationResponse response = organizationService.createOrganization(principal.getSubject(), request.name());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
