package org.prosallo.service;

import org.prosallo.data.OrganizationResponse;

import java.util.List;

public interface OrganizationService {

    OrganizationResponse createOrganization(String ownerId, String name);

    List<OrganizationResponse> listOrganizationsForUser(String userId);

    OrganizationResponse getOrganization(Long organizationId);
}
