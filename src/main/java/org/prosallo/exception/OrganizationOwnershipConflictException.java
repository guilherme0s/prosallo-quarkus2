package org.prosallo.exception;

import jakarta.ws.rs.core.Response;
import org.prosallo.infrastructure.exception.HttpResponseException;

public class OrganizationOwnershipConflictException extends HttpResponseException {

    public OrganizationOwnershipConflictException() {
        super(Response.Status.CONFLICT, "User already owns an organization");
    }
}
