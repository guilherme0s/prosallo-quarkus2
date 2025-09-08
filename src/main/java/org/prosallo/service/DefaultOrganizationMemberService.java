package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.exception.OrganizationMemberNotFoundException;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.exception.PermissionSetNotFoundException;
import org.prosallo.model.OrganizationMember;
import org.prosallo.model.PermissionSet;
import org.prosallo.repository.OrganizationMemberRepository;
import org.prosallo.repository.OrganizationRepository;
import org.prosallo.repository.PermissionSetRepository;

@ApplicationScoped
public class DefaultOrganizationMemberService implements OrganizationMemberService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final PermissionSetRepository permissionSetRepository;

    public DefaultOrganizationMemberService(OrganizationRepository organizationRepository,
            OrganizationMemberRepository organizationMemberRepository,
            PermissionSetRepository permissionSetRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMemberRepository = organizationMemberRepository;
        this.permissionSetRepository = permissionSetRepository;
    }

    @Override
    @Transactional
    public void assignPermissionSet(Long organizationId, Long memberId, Long permissionSetId) {
        organizationRepository.findById(organizationId).orElseThrow(OrganizationNotFoundException::new);

        OrganizationMember member = organizationMemberRepository.findById(memberId)
                .orElseThrow(OrganizationMemberNotFoundException::new);

        PermissionSet permissionSet = permissionSetRepository.findById(permissionSetId)
                .orElseThrow(PermissionSetNotFoundException::new);

        member.assignPermissionSet(permissionSet);
        organizationMemberRepository.save(member);
    }
}
