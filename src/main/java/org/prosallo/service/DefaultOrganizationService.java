package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.data.OrganizationResponse;
import org.prosallo.model.Organization;
import org.prosallo.model.OrganizationMember;
import org.prosallo.repository.OrganizationMemberRepository;
import org.prosallo.repository.OrganizationRepository;

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
        Organization organization = new Organization(name);
        organization = organizationRepository.save(organization);

        OrganizationMember organizationMember = new OrganizationMember(ownerId, organization);
        organizationMemberRepository.save(organizationMember);

        return new OrganizationResponse(organization.getId(), organization.getName());
    }
}
