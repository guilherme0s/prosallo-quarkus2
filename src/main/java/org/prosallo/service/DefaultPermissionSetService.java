package org.prosallo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.prosallo.data.PermissionSetResponse;
import org.prosallo.exception.OrganizationNotFoundException;
import org.prosallo.exception.PermissionSetConflictException;
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
                .orElseThrow(OrganizationNotFoundException::new);

        if (permissionSetRepository.existsByNameAndOrganizationId(permissionSetName, organizationId)) {
            throw new PermissionSetConflictException();
        }

        PermissionSet permissionSet = new PermissionSet(permissionSetName, organization);
        permissionSetRepository.save(permissionSet);

        return new PermissionSetResponse(permissionSet.getId(), permissionSet.getName());
    }
}
