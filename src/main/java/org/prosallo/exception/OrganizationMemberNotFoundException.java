package org.prosallo.exception;

public class OrganizationMemberNotFoundException extends RuntimeException {

    public OrganizationMemberNotFoundException() {
        super("Organization member not found");
    }
}
