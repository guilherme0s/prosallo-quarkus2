package org.prosallo.service;

import org.prosallo.data.InvitationRequest;
import org.prosallo.data.InvitationResponse;

public interface InvitationService {

    InvitationResponse createInvitation(Long organizationId, InvitationRequest request);

    void deleteInvitation(Long organizationId, Long invitationId);
}
