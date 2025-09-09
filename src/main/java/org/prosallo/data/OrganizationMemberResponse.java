package org.prosallo.data;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record OrganizationMemberResponse(
        @Schema(description = "The unique ID of the membership record.")
        Long id,
        @Schema(description = "The unique ID of the associated with this membership.")
        String userId) {}
