package org.prosallo.infrastructure.exception;

import static jakarta.ws.rs.core.Response.Status;

/**
 * An abstract base class for al custom API exceptions that can be mapped to an HTTP response.
 * <p>
 * This class encapsulates an HTTP status and a message, providing a common foundation for a
 * structured exception hierarchy.
 */
public abstract class HttpResponseException extends RuntimeException {

    private final Status status;

    /**
     * Constructs a new HttpResponseException.
     *
     * @param status The HTTP status to be returned to the client.
     * @param message The error message.
     */
    protected HttpResponseException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
