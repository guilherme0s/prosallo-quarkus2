package org.prosallo.infrastructure.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;

/**
 * A JAX-RS provider that maps {@link ConstraintViolationException} to standardized
 * HTTP 400 Bad Request response.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String DEFAULT_MESSAGE =
            "Your request could not be processed because it contains invalid or missing data.";

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var fieldErrors = new ArrayList<ErrorResponse.FieldError>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = getFieldName(violation.getPropertyPath());
            fieldErrors.add(new ErrorResponse.FieldError(fieldName, violation.getMessage()));
        }

        var statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        var response = new ErrorResponse(statusCode, DEFAULT_MESSAGE, fieldErrors);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private String getFieldName(Path propertyPath) {
        String fieldName = "";
        for (Path.Node node : propertyPath) {
            fieldName = node.getName();
        }
        return fieldName;
    }
}
