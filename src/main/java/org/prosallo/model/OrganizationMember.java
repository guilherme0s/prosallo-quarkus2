package org.prosallo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.prosallo.infrastructure.entity.AbstractAuditable;

@Entity
@Table(name = "organization_members")
public class OrganizationMember extends AbstractAuditable {

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, updatable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_set_id")
    private PermissionSet permissionSet;

    protected OrganizationMember() {
    }

    public OrganizationMember(String userId, Organization organization) {
        this.userId = userId;
        this.organization = organization;
    }

    public void assignPermissionSet(PermissionSet permissionSet) {
        this.permissionSet = permissionSet;
    }

    public PermissionSet getPermissionSet() {
        return permissionSet;
    }

    public String getUserId() {
        return userId;
    }

    public Organization getOrganization() {
        return organization;
    }
}
