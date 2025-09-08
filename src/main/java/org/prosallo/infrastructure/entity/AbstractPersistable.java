package org.prosallo.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import org.prosallo.infrastructure.identifier.Tsid;
import org.prosallo.infrastructure.persistence.Persistable;

@MappedSuperclass
public abstract class AbstractPersistable implements Persistable<Long> {

    @Id
    @Tsid
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private boolean isNew = true;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    @PostLoad
    void markIsNotNew() {
        this.isNew = false;
    }
}
