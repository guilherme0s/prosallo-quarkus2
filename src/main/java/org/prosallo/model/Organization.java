package org.prosallo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.prosallo.infrastructure.entity.AbstractAuditable;

@Entity
@Table(name = "organizations")
public class Organization extends AbstractAuditable {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private String ownerId;

    protected Organization() {
    }

    public Organization(String name, String ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
