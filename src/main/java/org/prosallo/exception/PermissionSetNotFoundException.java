package org.prosallo.exception;

public class PermissionSetNotFoundException extends RuntimeException {

    public PermissionSetNotFoundException() {
        super("Permission set not found");
    }
}
