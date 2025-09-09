package org.prosallo.repository;

import org.prosallo.infrastructure.persistence.CrudRepository;
import org.prosallo.infrastructure.persistence.Page;
import org.prosallo.infrastructure.persistence.Pageable;
import org.prosallo.model.OrganizationMember;

import java.util.List;

public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Long> {

    List<OrganizationMember> findAllByUserId(String userId);

    boolean existsByUserIdAndOrganizationId(String userId, Long organizationId);

    Page<OrganizationMember> findAllByOrganizationId(Long organizationId, Pageable pageable);
}
