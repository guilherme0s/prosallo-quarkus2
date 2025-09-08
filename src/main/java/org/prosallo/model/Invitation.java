package org.prosallo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.prosallo.infrastructure.entity.AbstractAuditable;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitations")
public class Invitation extends AbstractAuditable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, updatable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_set_id")
    private PermissionSet permissionSet;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "accepted", nullable = false)
    private boolean accepted = false;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    protected Invitation() {
    }

    public Invitation(
            Organization organization,
            PermissionSet permissionSet,
            String email,
            String token,
            LocalDateTime expiresAt) {
        this.organization = organization;
        this.permissionSet = permissionSet;
        this.email = email;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public boolean isPending() {
        return !this.accepted && !isExpired();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
