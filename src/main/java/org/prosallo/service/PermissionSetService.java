package org.prosallo.service;

import org.prosallo.data.PermissionSetResponse;

public interface PermissionSetService {

    PermissionSetResponse createPermissionSet(Long organizationId, String permissionSetName);
}
