package org.prosallo.exception;

import org.prosallo.infrastructure.exception.ResourceNotFoundException;

public class PermissionSetNotFoundException extends ResourceNotFoundException {

    public PermissionSetNotFoundException() {
        super("Permission set not found");
    }
}
