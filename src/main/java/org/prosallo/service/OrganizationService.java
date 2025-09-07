package org.prosallo.service;

import org.prosallo.data.OrganizationResponse;

public interface OrganizationService {

    OrganizationResponse createOrganization(String ownerId, String name);
}
