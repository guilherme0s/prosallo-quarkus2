package org.prosallo.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import org.prosallo.infrastructure.identifier.Tsid;

@MappedSuperclass
public abstract class AbstractPersistable {

    @Id
    @Tsid
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private boolean isNew = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markIsNotNew() {
        this.isNew = false;
    }
}
