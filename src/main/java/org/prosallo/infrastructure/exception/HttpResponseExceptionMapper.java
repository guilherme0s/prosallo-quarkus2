package org.prosallo.infrastructure.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * A JAX-RS provider that maps all subclasses of {@link HttpResponseException} to standardized
 * {@link Response}.
 * <p>
 * This acts as a central adapter, converting domain meaningful exceptions into well-formed HTTP
 * error responses with a JSON body.
 */
@Provider
public class HttpResponseExceptionMapper implements ExceptionMapper<HttpResponseException> {

    @Override
    public Response toResponse(HttpResponseException exception) {
        int statusCode = exception.getStatus().getStatusCode();
        ErrorResponse response = new ErrorResponse(statusCode, exception.getMessage());

        return Response.status(exception.getStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
