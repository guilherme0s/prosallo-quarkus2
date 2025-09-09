package org.prosallo.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * A token generator that uses a cryptographically strong random number generator.
 */
@ApplicationScoped
public class SecureRandomTokenGenerator implements TokenGenerator {

    private static final int TOKEN_LENGTH_BYTES = 32;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH_BYTES];
        secureRandom.nextBytes(tokenBytes);
        // Use URL-safe Base64 encoding to avoid issues with special characters in URLs.
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
