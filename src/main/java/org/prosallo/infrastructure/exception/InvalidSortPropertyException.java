package org.prosallo.infrastructure.exception;

public class InvalidSortPropertyException extends BadRequestException {

    public InvalidSortPropertyException(String propertyName) {
        super("Invalid sort property: " + propertyName);
    }
}
