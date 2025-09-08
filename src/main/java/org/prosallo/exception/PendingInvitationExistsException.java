package org.prosallo.exception;

import jakarta.ws.rs.core.Response;
import org.prosallo.infrastructure.exception.HttpResponseException;

public class PendingInvitationExistsException extends HttpResponseException {

    public PendingInvitationExistsException() {
        super(Response.Status.CONFLICT, "A pending invitation already exists for this user");
    }
}
