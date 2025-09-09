package org.prosallo.repository;

import org.prosallo.model.OrganizationMember;

import java.util.List;
import java.util.Optional;

public interface OrganizationMemberRepository {

    OrganizationMember save(OrganizationMember organizationMember);

    Optional<OrganizationMember> findById(Long id);

    boolean existsByUserIdAndOrganizationId(String userId, Long organizationId);

    List<OrganizationMember> findAllByUserId(String userId);
}
