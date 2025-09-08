package org.prosallo.service;

public interface OrganizationMemberService {

    void assignPermissionSet(Long organizationId, Long memberId, Long permissionSetId);
}
