package org.prosallo.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.InvitationRequest;
import org.prosallo.data.InvitationResponse;
import org.prosallo.infrastructure.security.SecurityContext;
import org.prosallo.service.InvitationService;

@Path("/api/v1/organizations/{organizationId}/invitations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationResource {

    private final SecurityContext securityContext;
    private final InvitationService invitationService;

    public InvitationResource(SecurityContext securityContext, InvitationService invitationService) {
        this.securityContext = securityContext;
        this.invitationService = invitationService;
    }

    @POST
    public Response createInvitation(@PathParam(value = "organizationId") Long organizationId,
            @Valid InvitationRequest request) {

        securityContext.requireOrganizationOwnership(organizationId);

        InvitationResponse response = invitationService.createInvitation(organizationId, request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
