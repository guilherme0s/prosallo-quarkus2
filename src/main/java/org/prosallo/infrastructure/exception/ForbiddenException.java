package org.prosallo.infrastructure.exception;

import jakarta.ws.rs.core.Response;

public class ForbiddenException extends HttpResponseException {

    public ForbiddenException(String message) {
        super(Response.Status.FORBIDDEN, message);
    }
}
