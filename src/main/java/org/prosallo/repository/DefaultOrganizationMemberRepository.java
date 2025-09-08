package org.prosallo.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.prosallo.infrastructure.persistence.AbstractCrudRepository;
import org.prosallo.model.OrganizationMember;

@ApplicationScoped
public class DefaultOrganizationMemberRepository extends AbstractCrudRepository<OrganizationMember, Long>
        implements OrganizationMemberRepository {

}
