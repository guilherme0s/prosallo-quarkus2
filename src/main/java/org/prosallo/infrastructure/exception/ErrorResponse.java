package org.prosallo.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

/**
 * A standard DTO for representing API validation error responses.
 *
 * @param status The HTTP status code (typically 400).
 * @param message A general summary of the error (e.g., "Validation failed").
 * @param errors A list of specific field errors.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(int status, String message, List<FieldError> errors) {

    /**
     * A standard DTO for representing API error responses.
     *
     * @param status The HTTP status code.
     * @param message A descriptive error message for the client.
     */
    public ErrorResponse(int status, String message) {
        this(status, message, Collections.emptyList());
    }

    /**
     * Represents a single validation error for a specific field.
     *
     * @param field The name of the field that failed validation.
     * @param message A description of the validation error.
     */
    public record FieldError(String field, String message) {}
}
