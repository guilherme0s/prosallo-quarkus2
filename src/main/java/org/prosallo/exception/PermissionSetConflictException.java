package org.prosallo.exception;

import jakarta.ws.rs.core.Response;
import org.prosallo.infrastructure.exception.HttpResponseException;

public class PermissionSetConflictException extends HttpResponseException {

    public PermissionSetConflictException() {
        super(Response.Status.CONFLICT, "A permission set with the same name already exists in this organization");
    }
}
