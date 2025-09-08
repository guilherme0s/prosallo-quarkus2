package org.prosallo.infrastructure.configuration;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

import java.time.Duration;

/**
 * Configuration properties for the invitation system.
 */
@ConfigMapping(prefix = "prosallo.invitation")
public interface InvitationConfiguration {

    /**
     * The duration for which an invitation is valid before it expires.
     * <p>
     * This value is configured in {@code application.properties} via the {@code prosallo.invitation.validity-duration} key.
     * It supports standard duration formats like "7d", "24h", "30m".
     */
    @WithDefault("7d")
    Duration validityDuration();
}
