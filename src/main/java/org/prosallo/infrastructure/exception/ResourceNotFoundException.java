package org.prosallo.infrastructure.exception;

import jakarta.ws.rs.core.Response.Status;

public class ResourceNotFoundException extends HttpResponseException {

    public ResourceNotFoundException(String message) {
        super(Status.NOT_FOUND, message);
    }
}
