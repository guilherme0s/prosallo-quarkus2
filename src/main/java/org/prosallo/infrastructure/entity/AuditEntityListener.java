package org.prosallo.infrastructure.entity;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.Optional;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof AbstractAuditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            auditable.setCreatedAt(now);
            auditable.setUpdatedAt(now);

            getCurrentUser().ifPresent(u -> {
                auditable.setCreatedBy(u);
                auditable.setUpdatedBy(u);
            });
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof AbstractAuditable auditable) {
            auditable.setUpdatedAt(LocalDateTime.now());
            getCurrentUser().ifPresent(auditable::setUpdatedBy);
        }
    }

    private Optional<String> getCurrentUser() {
        try (InstanceHandle<JsonWebToken> handle = Arc.container().instance(JsonWebToken.class)) {
            JsonWebToken jwt = handle.get();
            if (jwt != null) {
                Object sub = jwt.getClaim("sub");
                if (sub instanceof String) {
                    return Optional.of((String) sub);
                }
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
