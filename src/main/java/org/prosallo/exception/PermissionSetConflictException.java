package org.prosallo.exception;

public class PermissionSetConflictException extends RuntimeException {

    public PermissionSetConflictException(String permissionSetName) {
        super(String.format("Permission set %s already exists for this organization", permissionSetName));
    }
}
