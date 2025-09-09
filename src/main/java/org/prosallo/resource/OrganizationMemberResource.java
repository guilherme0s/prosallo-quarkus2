package org.prosallo.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.prosallo.data.AssignPermissionSetRequest;
import org.prosallo.data.OrganizationMemberResponse;
import org.prosallo.infrastructure.persistence.Direction;
import org.prosallo.infrastructure.persistence.Page;
import org.prosallo.infrastructure.persistence.Pageable;
import org.prosallo.infrastructure.persistence.Sort;
import org.prosallo.infrastructure.security.SecurityContext;
import org.prosallo.service.OrganizationMemberService;

@Path("/api/v1/organizations/{organizationId}/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationMemberResource {

    private final SecurityContext securityContext;
    private final OrganizationMemberService organizationMemberService;

    public OrganizationMemberResource(SecurityContext securityContext, OrganizationMemberService organizationMemberService) {
        this.securityContext = securityContext;
        this.organizationMemberService = organizationMemberService;
    }

    @GET
    public Response listMembers(
            @PathParam("organizationId") Long organizationId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("sort") @DefaultValue("id") String sort,
            @QueryParam("direction") @DefaultValue("ASC") String direction) {

        securityContext.requireMembership(organizationId);

        Sort sortObj = Sort.by(Direction.valueOf(direction.toUpperCase()), sort);
        Pageable pageable = new Pageable(page, size, sortObj);

        Page<OrganizationMemberResponse> memberPage = organizationMemberService.listMembers(organizationId, pageable);
        return Response.ok(memberPage).build();
    }

    @POST
    @Path("/{memberId}/permission-sets")
    public Response assignPermissionSet(@PathParam("organizationId") Long organizationId,
            @PathParam("memberId") Long memberId, @Valid AssignPermissionSetRequest request) {

        securityContext.requireOrganizationOwnership(organizationId);

        organizationMemberService.assignPermissionSet(organizationId, memberId, request.permissionSetId());
        return Response.ok().build();
    }
}
