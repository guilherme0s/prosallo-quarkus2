package org.prosallo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.prosallo.infrastructure.entity.AbstractAuditable;

@Entity
@Table(name = "permission_sets")
public class PermissionSet extends AbstractAuditable {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, updatable = false)
    private Organization organization;

    protected PermissionSet() {
    }

    public PermissionSet(String name, Organization organization) {
        this.name = name;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public Organization getOrganization() {
        return organization;
    }
}
