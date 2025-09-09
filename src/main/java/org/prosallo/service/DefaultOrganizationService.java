package org.prosallo.service;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.data.OrganizationResponse;
import org.prosallo.exception.OrganizationOwnershipConflictException;
import org.prosallo.model.Organization;
import org.prosallo.model.OrganizationMember;
import org.prosallo.repository.OrganizationMemberRepository;
import org.prosallo.repository.OrganizationRepository;

import java.util.List;

@ApplicationScoped
public class DefaultOrganizationService implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    public DefaultOrganizationService(OrganizationRepository organizationRepository,
            OrganizationMemberRepository organizationMemberRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMemberRepository = organizationMemberRepository;
    }

    @Override
    @Transactional
    public OrganizationResponse createOrganization(String ownerId, String name) {
        if (organizationRepository.existsByOwnerId(ownerId)) {
            throw new OrganizationOwnershipConflictException();
        }

        Organization organization = new Organization(name, ownerId);
        organization = organizationRepository.save(organization);

        OrganizationMember organizationMember = new OrganizationMember(ownerId, organization);
        organizationMemberRepository.save(organizationMember);

        return new OrganizationResponse(organization.getId(), organization.getName());
    }

    @Override
    @CacheResult(cacheName = "user-organizations")
    public List<OrganizationResponse> listOrganizationsForUser(String userId) {
        return organizationMemberRepository.findAllByUserId(userId).stream()
                .map(OrganizationMember::getOrganization)
                .map(org -> new OrganizationResponse(org.getId(), org.getName()))
                .toList();
    }
}
