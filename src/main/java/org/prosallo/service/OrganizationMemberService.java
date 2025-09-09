package org.prosallo.service;

import org.prosallo.data.OrganizationMemberResponse;
import org.prosallo.infrastructure.persistence.Page;
import org.prosallo.infrastructure.persistence.Pageable;

public interface OrganizationMemberService {

    void assignPermissionSet(Long organizationId, Long memberId, Long permissionSetId);

    Page<OrganizationMemberResponse> listMembers(Long organizationId, Pageable pageable);
}
