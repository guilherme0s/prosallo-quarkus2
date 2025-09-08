package org.prosallo.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.PermissionSetRequest;
import org.prosallo.data.PermissionSetResponse;
import org.prosallo.infrastructure.security.SecurityContext;
import org.prosallo.service.PermissionSetService;

@Path("/api/v1/organizations/{organizationId}/permission-sets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PermissionSetResource {

    private final SecurityContext securityContext;
    private final PermissionSetService permissionSetService;

    public PermissionSetResource(SecurityContext securityContext, PermissionSetService permissionSetService) {
        this.securityContext = securityContext;
        this.permissionSetService = permissionSetService;
    }

    @POST
    public Response create(@PathParam("organizationId") Long organizationId, @Valid PermissionSetRequest request) {
        securityContext.requireOrganizationOwnership(organizationId);
        PermissionSetResponse response = permissionSetService.createPermissionSet(organizationId, request.name());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
