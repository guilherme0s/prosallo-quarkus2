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

    protected Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
