package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.data.OrganizationMemberResponse;
import org.prosallo.exception.OrganizationMemberNotFoundException;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.exception.PermissionSetNotFoundException;
import org.prosallo.infrastructure.persistence.Page;
import org.prosallo.infrastructure.persistence.Pageable;
import org.prosallo.model.OrganizationMember;
import org.prosallo.model.PermissionSet;
import org.prosallo.repository.OrganizationMemberRepository;
import org.prosallo.repository.OrganizationRepository;
import org.prosallo.repository.PermissionSetRepository;

import java.util.List;

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

    @Override
    public Page<OrganizationMemberResponse> listMembers(Long organizationId, Pageable pageable) {
        Page<OrganizationMember> memberPage = organizationMemberRepository
                .findAllByOrganizationId(organizationId, pageable);

        List<OrganizationMemberResponse> content = memberPage.content().stream()
                .map(member -> new OrganizationMemberResponse(member.getId(), member.getUserId()))
                .toList();

        return new Page<>(content, memberPage.totalElements(), pageable.pageNumber(), pageable.pageSize());
    }
}
