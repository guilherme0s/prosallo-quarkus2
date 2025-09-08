package org.prosallo.exception;

import org.prosallo.infrastructure.exception.ResourceNotFoundException;

public class OrganizationMemberNotFoundException extends ResourceNotFoundException {

    public OrganizationMemberNotFoundException() {
        super("The requested organization member could not be found");
    }
}
