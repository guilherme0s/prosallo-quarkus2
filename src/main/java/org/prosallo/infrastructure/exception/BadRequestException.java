package org.prosallo.infrastructure.exception;

import jakarta.ws.rs.core.Response;

public class BadRequestException extends HttpResponseException {

    public BadRequestException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }
}
