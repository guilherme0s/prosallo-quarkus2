package org.prosallo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionSetRequest(@NotBlank @Size(min = 1, max = 100) String name) {}
