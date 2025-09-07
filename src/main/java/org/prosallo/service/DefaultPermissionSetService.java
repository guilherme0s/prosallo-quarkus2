package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.prosallo.data.PermissionSetResponse;
import org.prosallo.model.Organization;
import org.prosallo.model.PermissionSet;
import org.prosallo.repository.OrganizationRepository;
import org.prosallo.repository.PermissionSetRepository;

@ApplicationScoped
public class DefaultPermissionSetService implements PermissionSetService {

    private final OrganizationRepository organizationRepository;
    private final PermissionSetRepository permissionSetRepository;

    public DefaultPermissionSetService(OrganizationRepository organizationRepository,
            PermissionSetRepository permissionSetRepository) {
        this.organizationRepository = organizationRepository;
        this.permissionSetRepository = permissionSetRepository;
    }

    @Override
    @Transactional
    public PermissionSetResponse createPermissionSet(Long organizationId, String permissionSetName) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));

        PermissionSet permissionSet = new PermissionSet(permissionSetName, organization);
        permissionSet = permissionSetRepository.save(permissionSet);

        return new PermissionSetResponse(permissionSet.getId(), permissionSet.getName());
    }
}
