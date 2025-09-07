package org.prosallo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrganizationRequest(@NotBlank @Size(min = 3, max = 100) String name) {}
