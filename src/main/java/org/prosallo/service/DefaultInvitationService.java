package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.data.InvitationRequest;
import org.prosallo.data.InvitationResponse;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.exception.PendingInvitationExistsException;
import org.prosallo.exception.PermissionSetNotFoundException;
import org.prosallo.infrastructure.configuration.InvitationConfiguration;
import org.prosallo.infrastructure.exception.ResourceNotFoundException;
import org.prosallo.infrastructure.security.TokenGenerator;
import org.prosallo.mapper.InvitationMapper;
import org.prosallo.model.Invitation;
import org.prosallo.model.Organization;
import org.prosallo.model.PermissionSet;
import org.prosallo.repository.InvitationRepository;
import org.prosallo.repository.OrganizationRepository;
import org.prosallo.repository.PermissionSetRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class DefaultInvitationService implements InvitationService {

    private final OrganizationRepository organizationRepository;
    private final PermissionSetRepository permissionSetRepository;
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final InvitationConfiguration invitationConfiguration;
    private final TokenGenerator tokenGenerator;

    public DefaultInvitationService(OrganizationRepository organizationRepository,
            PermissionSetRepository permissionSetRepository,
            InvitationRepository invitationRepository,
            InvitationMapper invitationMapper,
            InvitationConfiguration invitationConfiguration,
            TokenGenerator tokenGenerator) {
        this.organizationRepository = organizationRepository;
        this.permissionSetRepository = permissionSetRepository;
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
        this.invitationConfiguration = invitationConfiguration;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    @Transactional
    public InvitationResponse createInvitation(Long organizationId, InvitationRequest request) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(OrganizationNotFoundException::new);

        PermissionSet permissionSet = permissionSetRepository
                .findByIdAndOrganizationId(request.permissionSetId(), organizationId)
                .orElseThrow(PermissionSetNotFoundException::new);

        if (invitationRepository.existsPendingByOrganizationIdAndEmail(organizationId, request.email())) {
            throw new PendingInvitationExistsException();
        }

        String token = tokenGenerator.generateToken();
        LocalDateTime expiresAt = LocalDateTime.now().plus(invitationConfiguration.validityDuration());

        Invitation invitation = new Invitation(organization, permissionSet, request.email(), token, expiresAt);
        invitation = invitationRepository.save(invitation);

        return invitationMapper.toResponse(invitation);
    }

    @Override
    @Transactional
    public void deleteInvitation(Long organizationId, Long invitationId) {
        Invitation invitation = invitationRepository.findByIdAndOrganizationId(invitationId, organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        invitationRepository.delete(invitation);
    }
}
