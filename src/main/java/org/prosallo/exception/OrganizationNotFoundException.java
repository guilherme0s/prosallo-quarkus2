package org.prosallo.exception;

import org.prosallo.infrastructure.exception.ResourceNotFoundException;

public class OrganizationNotFoundException extends ResourceNotFoundException {

    public OrganizationNotFoundException() {
        super("The requested organization could not be found");
    }
}
