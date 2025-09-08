package org.prosallo.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.AssignPermissionSetRequest;
import org.prosallo.service.OrganizationMemberService;

@Path("/api/v1/organizations/{organizationId}/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationMemberResource {

    private final OrganizationMemberService organizationMemberService;

    public OrganizationMemberResource(OrganizationMemberService organizationMemberService) {
        this.organizationMemberService = organizationMemberService;
    }

    @POST
    @Path("/{memberId}/permission-sets")
    public Response assignPermissionSet(@PathParam("organizationId") Long organizationId,
            @PathParam("memberId") Long memberId, @Valid AssignPermissionSetRequest request) {
        organizationMemberService.assignPermissionSet(organizationId, memberId, request.permissionSetId());
        return Response.ok().build();
    }
}
