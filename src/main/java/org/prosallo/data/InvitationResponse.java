package org.prosallo.data;

import java.time.LocalDateTime;

public record InvitationResponse(Long id, String email, LocalDateTime expiresAt) {}
